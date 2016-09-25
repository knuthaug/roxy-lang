// Generated from src/main/antlr4/roxy/antlr/Roxy.g4 by ANTLR 4.5.3
package roxy.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RoxyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RoxyVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RoxyParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(RoxyParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link RoxyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(RoxyParser.ExprContext ctx);
}