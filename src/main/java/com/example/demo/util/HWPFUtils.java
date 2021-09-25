package com.example.demo.util;

import com.example.demo.VO.Font_stype;
import com.example.demo.VO.Paragraph_stype;
import com.example.demo.VO.Table;
import com.example.demo.VO.Title;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public final class HWPFUtils {

    private HWPFUtils() { }

    public static void parse(InputStream is,String token) throws IOException {
        new HWPFHelper(is).parseAndWrite(token);
    }

    private static class HWPFHelper {
        HWPFDocument hwpfDocument;
        PicturesTable picturesTable;
        List<com.example.demo.VO.Paragraph> paragraphs = new ArrayList<>();
        Map<Integer, Picture> pictures = new HashMap<>();
        List<List<Font_stype>> fonts = new ArrayList<>();
        List<com.example.demo.VO.Picture> picModels = new ArrayList<>();
        List<Title> titles = new ArrayList<>();
        List<Paragraph_stype> paragraph_stypes = new ArrayList<>();
        List<Table> tables = new ArrayList<>();

        HWPFHelper(InputStream is) throws IOException {
            hwpfDocument = new HWPFDocument(is);
            picturesTable = hwpfDocument.getPicturesTable();
            getPicOffsets(pictures, picturesTable);
        }

        static void getPicOffsets(Map<Integer, Picture> pictures, PicturesTable picturesTable) {
            List<Picture> allPics = picturesTable.getAllPictures();
            for (Picture pic: allPics) {
                pictures.put(pic.getStartOffset(), pic);
            }
        }

        static boolean isTitle(Paragraph paragraph) {
            LineSpacingDescriptor lineSpacingDescriptor = paragraph.getLineSpacing();
            int ilfo = paragraph.getIlfo();
            boolean isKinsoku = paragraph.isKinsoku();
            short style = paragraph.getStyleIndex();
            ParagraphProperties properties = paragraph.getProps();
            int left = properties.getLeftBorder().toInt();
            int right = properties.getRightBorder().toInt();
            boolean isLineNotNumbered = properties.isLineNotNumbered();
            boolean isBackward = properties.isBackward();
            boolean isVertical = properties.isVertical();
            boolean keepWithNext = properties.keepWithNext();
            boolean isSideBySide = properties.isSideBySide();
            return false;
        }

        static Title extractAsTitle(Paragraph paragraph, int defaultId) {
            Title title = new Title();
            title.setFirstLineIndent(paragraph.getFirstLineIndent());
            title.setIndentFromLeft(paragraph.getIndentFromLeft());
            title.setIndentFromRight(paragraph.getIndentFromRight());
            title.setLineSpacing((double) paragraph.getLineSpacing().toInt());
            title.setLvl(paragraph.getLvl());
            title.setParagraphText(paragraph.text());
            title.setParagraphId(BigInteger.valueOf(defaultId));
            return title;
        }

        void parse() {
            Range r = hwpfDocument.getRange();
            com.example.demo.VO.Paragraph paragraph; Paragraph p, previousParagraph = null;
            int j=0;

            for (int i = 0, n = r.numParagraphs(); i < n; i++) {
                p = r.getParagraph(i);
                /*
                 * 首先要确认这个段落是不是一个title
                 * 确认的方法（暂时是简单粗暴的）以是否为一行居中的文字作为判断标题的依据
                 * 如果不是title，那么就是一个普通的段落或者是在表格内的段落，这时候来判断表格
                 */
                if (isTitle(p)) {
                    Title title = extractAsTitle(p, i);
                    titles.add(title);
                }
                else {
                    paragraph = new com.example.demo.VO.Paragraph();
                    paragraph.setParagraphId(BigInteger.valueOf(i));
                    parseToModel(p, paragraph);

                    if (!paragraph.isInTable()) {
                        paragraphs.add(paragraph);
                    }
                    else {
                        /*
                         * 建表格
                         * 获取Document中的表格需要知道表格的起始段落，因此通过
                         * 每次都判断段落是否在表格中来找到表格中第一个段落。
                         * 然后进入循环，直到处理完第一个不在表格中的段落为止
                         * 此时的p跟paragraph都对应到table的paragraphAfter
                         */
                        Table table = new Table();
                        Paragraph firstInTable = p;

                        assert previousParagraph != null;
                        table.setTextBefore(previousParagraph.text());
                        table.setParagraphBefore(BigInteger.valueOf(i - 1));
                        paragraphs.add(paragraph);
                        do {
                            p = r.getParagraph(++i);
                            paragraph = new com.example.demo.VO.Paragraph();
                            paragraph.setParagraphId(BigInteger.valueOf(i));
                            parseToModel(p, paragraph);
                            paragraphs.add(paragraph);
                        } while (p.isInTable());
                        table.setTableContent(r.getTable(firstInTable).text());
                        table.setParagraphAfter(paragraph.getParagraphId());
                        table.setTextAfter(paragraph.getParagraphText());

                    }
                }

                previousParagraph = p;
            }
        }

        void parseAndWrite(String token) {
            parse();
            write(token);
        }

        void write(String token) {
            IOUtils.saveParserResult(paragraphs,tables,titles,fonts,picModels,paragraph_stypes,token);
        }

        private static void fill(org.apache.poi.hwpf.usermodel.Paragraph paragraph, com.example.demo.VO.Paragraph paragraphModel) {
            paragraphModel.setLvl(paragraph.getLvl());
            paragraphModel.setLineSpacing((double) paragraph.getLineSpacing().toInt());
            paragraphModel.setFirstLineIndent(paragraph.getFirstLineIndent());
            paragraphModel.setIndentFromLeft(paragraph.getIndentFromLeft());
            paragraphModel.setIndentFromRight(paragraph.getIndentFromRight());
            paragraphModel.setParagraphText(paragraph.text());
            paragraphModel.setInTable(paragraph.isInTable());
        }

        private static Font_stype extractFont(CharacterRun run) {
            Font_stype font = new Font_stype();
            font.setBold(run.isBold());
            font.setColor(run.getColor());
            font.setItalic(run.isItalic());
            font.setFontName(run.getFontName());
            font.setFontSize((double) run.getFontSize());
            return font;
        }

        private static com.example.demo.VO.Picture extractPicture(Picture picture) {
            com.example.demo.VO.Picture pictureModel = new com.example.demo.VO.Picture();
            pictureModel.setBase64Content(new BASE64Encoder().encode(picture.getContent()));
            pictureModel.setSuggestFileExtension(picture.suggestFileExtension());
            pictureModel.setWidth((double) picture.getWidth());
            pictureModel.setHeight((double) picture.getHeight());
            pictureModel.setFilename(picture.suggestFullFileName());
            return pictureModel;
        }

        private void parseToModel(Paragraph paragraph, com.example.demo.VO.Paragraph paragraphModel) {
            int numRuns = paragraph.numCharacterRuns();
            CharacterRun run, previousRun = null;
            Set<Font_stype> font_stypes = new HashSet<>();
            for (int j = 0; j < numRuns; j++) {
                run = paragraph.getCharacterRun(j);
                if (picturesTable.hasPicture(run)) {
                    Picture picture = pictures.get(run.getPicOffset());
                    com.example.demo.VO.Picture pictureModel = extractPicture(picture);
                    pictureModel.setTextBefore(previousRun.text());
                    picModels.add(pictureModel);
                }
                else {
                    Font_stype font = extractFont(run);
                    font.setFontAlignment(paragraph.getFontAlignment());
                    font_stypes.add(font);

                    // 回填picture

                }
                previousRun = run;
            }
            paragraphModel.setBold(font_stypes.stream().map(Font_stype::isBold).collect(Collectors.toList()));
            paragraphModel.setItalic(font_stypes.stream().map(Font_stype::isItalic).collect(Collectors.toList()));
            paragraphModel.setFontSize(font_stypes.stream().map(Font_stype::getFontSize).collect(Collectors.toList()));
            paragraphModel.setFontName(font_stypes.stream().map(Font_stype::getFontName).collect(Collectors.toList()));
            fonts.add(new ArrayList<>(font_stypes));
            fill(paragraph, paragraphModel);
        }
    }
}
