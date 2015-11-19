package com.ydhdj.fyzh.pdf;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import com.ydhdj.fyzh.service.BookService;


public class PdfPic {
	public static List<String> extractCovers(ByteBuffer buf,final String name){
		try {
			PDFFile file = new PDFFile(buf);
			int pages = file.getNumPages();
			int cnt = (pages>3)?3:pages;
			List<String> covers = new ArrayList<String>();
			for (int i = 1; i <= cnt; ++i) {
				PDFPage page = file.getPage(i);
				int width = (int) page.getBBox().getWidth();
				int height = (int) page.getBBox().getHeight();
				BufferedImage img = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D graph = img.createGraphics();
				graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				PDFRenderer render = new PDFRenderer(page, graph,
						new Rectangle(0, 0, width, height), null, Color.WHITE);
				page.waitForFinish();
				render.run();
				graph.dispose();
				//
				File dir = new File(BookService.COVER_PATH);
				dir.mkdirs();
				StringBuilder builder = new StringBuilder(UUID.randomUUID()
						.toString());
				builder.append(name).append("_cover_").append(i).append(".png");
				covers.add(builder.toString());
				File output = new File(dir, builder.toString());
				ImageIO.write(img, "png", output);
			}
			return covers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
