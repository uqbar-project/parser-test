package org.uqbar.testing

import scala.language.implicitConversions
import scala.language.reflectiveCalls
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.CharSequenceReader
import org.scalatest.Matchers
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.MatchResult

trait ParserTest[ParsersDefinition <: Parsers { type Elem = Char }] extends Matchers {

	case class beParsedTo[T](expected: T)(implicit parser: ParsersDefinition#Parser[T]) extends Matcher[String] {
		def apply(target: String) = {
			val result = parser.apply(new CharSequenceReader(target))

			MatchResult(
				result.successful && result.get == expected,
				if (result.successful) s"Parsed ${result.get} did not equal $expected" else s"Parse failed! $result",
				if (result.successful) s"Parsed ${result.get} was equal to $expected" else s"Parse didn't fail! $result"
			)
		}
	}

	case class beParsed[T](implicit parser: ParsersDefinition#Parser[T]) extends Matcher[String] {
		def apply(target: String) = {
			val result = parser(new CharSequenceReader(target))
			MatchResult(
				result.successful,
				s"Parse failed: $result",
				s"Parse didn't fail! $result"
			)
		}
	}

}