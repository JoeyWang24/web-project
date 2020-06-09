package com.Share.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import com.Share.exception.FileUploadException;
import com.sun.awt.AWTUtilities.Translucency;


public class ThumbnailUtil {

    //缩略图的宽高
    public static final Integer LENGTH = 200;

    /**
     * 将指定文件生成缩略图,缩略图居中，宽高相等，图片按比例缩放，四周空白处透明背景
     *
     * @param filepath
     * @return
     * @throws IOException
     */
    public static String createThumbnail(HttpServletRequest request, String filepath) throws FileUploadException {
        try {
            String fPath = request.getServletContext().getRealPath("/") + filepath;
            Image sourceImg = ImageIO.read(new File(request.getServletContext().getRealPath("/") + filepath));
            int sourceWidth = sourceImg.getWidth(null);
            int sourceHeight = sourceImg.getHeight(null);

            String filename = FileUtil.renamePng();
            String thumbnailPath = request.getServletContext().getRealPath("/") + FileUtil.UPLOAD_DIR + "/" + filename;

            if (sourceWidth > sourceHeight) {
                clip(fPath, thumbnailPath, sourceHeight, sourceHeight);

            } else {
                clip(fPath, thumbnailPath, sourceWidth, sourceWidth);
            }

            return FileUtil.UPLOAD_DIR + "/" + filename;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException("生成缩略图异常！");
        }
    }

    private static void clip(String sourceFile, String targetFile, int width, int height) throws Exception {
        Thumbnails.of(sourceFile)
                .sourceRegion(Positions.CENTER, width, height)
                .size(LENGTH, LENGTH)
                .keepAspectRatio(false)
                .toFile(targetFile);
    }

}
