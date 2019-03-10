package org.kylin.service.pfive;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.kylin.util.CommonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public abstract class AbstractDocumentExporter<T> {

    protected static final String BASE_PATH = "/var/attachment/";

    protected XWPFDocument doc;

    protected T data;

    public AbstractDocumentExporter(XWPFDocument doc, T data) {
        this.doc = doc;
        this.data = data;
    }

    public void init(){
        if(Objects.isNull(data)){
            throw new RuntimeException("Data is NULL");
        }

        if(Objects.isNull(doc)){
            doc = new XWPFDocument();
        }
    }
    public void writeDefaultDocHeader(){
        writeDocHeader(null);
    }
    public void writeDocHeader(String title){
        XWPFParagraph header = doc.createParagraph();
        header.setVerticalAlignment(TextAlignment.TOP);
        header.setWordWrap(true);
        header.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun hr1 = header.createRun();
        hr1.setText(toUTF8(StringUtils.isBlank(title) ? "《我要发·排列5》福彩3D预测报表" : title));
        hr1.setBold(true);
        hr1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        hr1.setTextPosition(20);
        hr1.setFontSize(28);
        hr1.addBreak();
        writeStats();
    }

    public abstract void writeStats();


    public abstract void writeBody();

    public String exportCodes() throws IOException{
        return exportCodes(null, null);
    }

    public String exportCodes(String path, String targetFileName) throws IOException{

        if(StringUtils.isBlank(path)){
            path = BASE_PATH;
        }

        if(!path.endsWith("/")){
            path += "/";
        }

        String fileName = StringUtils.isBlank(targetFileName) ? CommonUtils.getCurrentTimeString() : (targetFileName );
        String subDirectory = fileName.substring(0,6);
        String targetDirName = path + subDirectory;

        if(!CommonUtils.createDirIfNotExist(targetDirName)){
            log.info("save-wCodes-create-directory-error targetDirName={}", targetDirName);
            throw new IOException("directory create error");
        }


        String exportName = fileName + "_deleted_codes.docx";

        // 保存
        StringBuilder sb = new StringBuilder();
        sb.append(targetDirName);
        sb.append(File.separator);
        sb.append(exportName);
        FileOutputStream out = new FileOutputStream(sb.toString());
        doc.write(out);
        out.close();

        return exportName;
    }


    protected static String toUTF8(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }
        return str;
    }

}
