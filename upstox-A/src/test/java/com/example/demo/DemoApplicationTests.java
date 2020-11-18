package com.example.demo;

import com.example.demo.model.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.awt.CharsetString;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.internal.util.io.IOUtil.close;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "{\"sym\":\"XETHZUSD\", \"T\":\"Trade\", \"P\":226.85, \"Q\":4.98,\"TS\":1538409733.3502, \"side\": \"b\", \"TS2\":1538409738828643608}";
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		Trade trade = objectMapper.readValue(jsonString, new TypeReference<Trade>() {});
		System.out.println(trade);
	}

	@Test
	void readfile() throws IOException {
		String encoding = "UTF-8";
		int maxlines = 100;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		StringWriter sw = new StringWriter();

		BufferedWriter stringWriter = new BufferedWriter(sw);

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("trades.json"), encoding));
			int count = 0;
			for (String line; (line = reader.readLine()) != null;) {
				if (count++ % maxlines == 0) {
					close(writer);
//					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("smallfile" + (count / maxlines) + ".txt"), encoding));
					writer = new BufferedWriter(sw);
//					writer= stringWriter;
					System.out.println("--------------------------------------");
					System.out.println(sw.toString());
					System.out.println("--------------------------------------");

				}
				writer.write(line);
				writer.newLine();
			}
		} finally {
			close(writer);
			close(reader);
		}
	}

	@Test
	void readFileGuava() throws IOException {
		List<String> strings = com.google.common.io.Files
				.readLines(new File("trades.json"), Charsets.UTF_8);
		strings.forEach(System.out::println);
	}


	@Test
	void readFile() throws IOException {

		try (SeekableByteChannel ch = Files.newByteChannel(Paths.get("trades.json"))) {
			ByteBuffer bb = ByteBuffer.allocateDirect(1000);
			for(;;) {
				StringBuilder line = new StringBuilder();

				int n = ch.read(bb);
				// add chars to line
				// ...

				System.out.println(n);
			}
		}
	}


}
