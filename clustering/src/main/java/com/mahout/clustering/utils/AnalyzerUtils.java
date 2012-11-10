package com.mahout.clustering.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

@SuppressWarnings("all")
public class AnalyzerUtils {
	public static void displayTokens(Analyzer analyzer, String text,
			PrintWriter out) throws IOException {
		displayTokens(analyzer.tokenStream("contents", new StringReader(text)),
				out);
	}

	public static void displayTokens(TokenStream stream, PrintWriter out)
			throws IOException {
		TermAttribute term = stream.addAttribute(TermAttribute.class);
		while (stream.incrementToken()) {
			out.print("[" + term.term() + "] ");
		}
	}

	public static void displayTokensWithFullDetails(Analyzer analyzer,
			String text, PrintWriter out) throws IOException {
		TokenStream stream = analyzer.tokenStream("contents", new StringReader(
				text));
		TermAttribute term = stream.addAttribute(TermAttribute.class);
		PositionIncrementAttribute posIncr = stream
				.addAttribute(PositionIncrementAttribute.class);
		OffsetAttribute offset = stream.addAttribute(OffsetAttribute.class);
		TypeAttribute type = stream.addAttribute(TypeAttribute.class);
		int position = 0;
		while (stream.incrementToken()) {
			int increment = posIncr.getPositionIncrement();
			if (increment > 0) {
				position = position + increment;
				out.println();
				out.print(position + ": ");
			}
			out.print("[" + term.term() + ":" + offset.startOffset() + "->"
					+ offset.endOffset() + ":" + type.type() + "] ");
		}
		out.println();
	}

	public static void displayTokensWithPositions(Analyzer analyzer, String text, PrintWriter out)
			throws IOException {
		TokenStream stream = analyzer.tokenStream("contents", new StringReader(
				text));
		TermAttribute term = stream.addAttribute(TermAttribute.class);
		PositionIncrementAttribute posIncr = stream
				.addAttribute(PositionIncrementAttribute.class);
		int position = 0;
		while (stream.incrementToken()) {
			int increment = posIncr.getPositionIncrement();
			if (increment > 0) {
				position = position + increment;
				out.println();
				out.print(position + ": ");
			}
			out.print("[" + term.term() + "] ");
		}
		out.println();
	}
}
