package org.uqbar.testing

import org.scalatest.FreeSpec
import scala.util.parsing.combinator.RegexParsers

trait ParserExample extends RegexParsers {
	val numberParser = "\\d+".r ^^ { _.toInt }
}

class ParseMatchersTest extends FreeSpec with ParserTest[ParserExample] with ParserExample {

	implicit val parser = numberParser
	
	"ParserTest's" - {
		
		"beParsed matcher" - {
		
			"should pass when parser succed" in {
				"42" should beParsed[Int]
			}
			
			"should not pass when parser fails" in {
				"foo" should not (beParsed[Int])
			}

		}

		"beParsedTo matcher" - {
		
			"should pass when parser succed and result equals expected value" in {
				"42" should beParsedTo (42)
			}
			
			"should not pass when parser fails" in {
				"foo" should not (beParsedTo(42))
			}

			"should not pass when parser succed but result doesn't equals expected value" in {
				"42" should not (beParsedTo(9))
			}
		
		}
		
	}
}