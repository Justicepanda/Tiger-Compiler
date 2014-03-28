package parser;

import compiler.TokenTuple;
import compiler.Compiler;

class ParserException extends RuntimeException {
	public ParserException(TokenTuple actual, TokenTuple expected) {
		super("\"" + actual.getToken() + "\" is not a valid token. Expected \""
				+ expected.getToken() + "\".");
		Compiler.errorOccured();
	}
}