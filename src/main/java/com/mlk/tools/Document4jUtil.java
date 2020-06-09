package com.mlk.tools;

import cn.hutool.core.io.FileUtil;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 功能描述: word转PDF **Linux系统暂不支持
 * @author: mlk
 * @date: 2020/6/9
 */
@Slf4j
public class Document4jUtil {

    public static String wordToPdf(String inputWord,String outputPdf) {
        String filePath = outputPdf;
        File outputFile = new File(filePath);
        FileUtil.mkParentDirs(outputFile);
        InputStream docxInputStream = null;
        OutputStream outputStream = null;
        try  {
            docxInputStream = new FileInputStream(inputWord);
            outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            log.debug("wordToPdf成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("wordToPdf异常！inputWord: " + inputWord);
            return null;
        }finally {
            try {
                outputStream.close();
                docxInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

}