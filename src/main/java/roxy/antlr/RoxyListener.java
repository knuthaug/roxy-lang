// Generated from src/main/antlr4/roxy/antlr/Roxy.g4 by ANTLR 4.5.3
package roxy.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RoxyParser}.
 */
public interface RoxyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RoxyParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(RoxyParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link RoxyParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(RoxyParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link RoxyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(RoxyParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RoxyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(RoxyParser.ExprContext ctx);
}