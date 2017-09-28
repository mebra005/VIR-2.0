package com.vir.service.impl;

import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vir.model.FileType;
import com.vir.model.Text;
import com.vir.service.FileProcessorService;
import com.vir.service.TextProcessorService;

@Service("docProcessorService")
public class DocProcessorService implements FileProcessorService {
	
	@Autowired
	@Qualifier("simpleTextProcessorService")
	private TextProcessorService textProcessorService;

	@Override
	public Text process(MultipartFile file, FileType type) throws Exception{
		
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);

		ParseContext parseContext = new ParseContext();

		InputStream stream = file.getInputStream();
		parser.parse(stream, handler, new Metadata(), parseContext);

		return textProcessorService.process(handler.toString());

	}

}