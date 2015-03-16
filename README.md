Parser Test
===========

[Scalatest](http://www.scalatest.org/) extension to test parsers made with [Scala Standard Parser Combinator Library](https://github.com/scala/scala-parser-combinators).

Setup
-----

To include this plugin in your *SBT* project, just add the following lines to your `.sbt` project definition:

```scala
resolvers += "Uqbar Central" at "http://uqbar-wiki.org/mvn/releases"

libraryDependencies += "org.uqbar" %% "parser-test" % "latest.integration" % "test"
```

Or, if you are using Uqbar's [SBT Flexible Dependencies Plugin](https://github.com/uqbar-project/sbt-flexible-dependencies-plugin):

```scala
lazy val project = FDProject(
	"org.uqbar" %% "parser-test" % "latest.integration" % "test"
)
```

Usage
-----

### Creating a Test

To create a parser test just create a test class extending your favorite *Scalatest* style and mix in `org.uqbar.testing.ParserTest[T]` being `T` the type of your parser definition.

```scala
import scala.util.parsing.combinator.RegexParsers
import org.uqbar.testing.ParserTest

class MyAwesomeParser extends RegexParsers {
	...
}

class MyParserTest extends FreeSpec with ParserTest[MyAwesomeParser] {
	...
}
```

Now write your tests the way you always do!

### Matchers Description

This extension provides the following matchers:

#### beParsedTo

`beParsedTo[T](expected: T)(implicit parser: Parser[T])`

Use this matcher to test whether a string gets parsed to the expected result or not.
```scala
"42" should beParsedTo (Number(42))(numberParser)
```

#### beParsed

`beParsed[T](implicit parser: Parser[T])`

Use this matcher to test whether a string gets parsed by a parser or not, disregarding what exactly it gets parsed to.
```scala
"42" should beParsed()(numberParser)
```

### Tips & Tricks

For all the matchers, the tested parser can also be declared as an implicit value in the test context:
```scala
implicit val parser: Parser[Number] = numberParser

"42" should beParsedTo (Number(42))

"42" should not (beParsedTo (Number(42)))

"00" should beParsed[Number]
```

Optionally, many tests can be simplified by shortening the expected result construction by using implicit conversions and picking the implicit parser with type arguments:   
```scala
implicit val parser: Parser[Client] = clientParser
implicit def String_to_Product(name: String) = new Product(new Identifier(name))

"Prod >> cheese" should beParsedTo [Product] ("cheese")
```

If you have intermediate parsers definitions that you would rather not make public but still would like to test, make your parser definition a trait
and mix it with the test class to test any *protected* parser:

```scala
import scala.util.parsing.combinator.RegexParsers
import org.uqbar.testing.ParserTest

object MyAwesomeParser extends MyAwesomeParser
trait MyAwesomeParser extends RegexParsers {
	lazy val foo: Parser[Foo] = fooPart ~ otherFooPart
	protected lazy val fooPart = ...
	protected lazy val otherFooPart = ...
}

class MyParserTest extends FreeSpec with ParserTest[MyAwesomeParser] with MyAwesomeParser {
	// Here you can test the foo parser, but also fooPart and otherFooPart as well!
}
```


Contributions
-------------

Yes, please! Pull requests are always welcome, just try to keep it small and clean.

License
-------

This code is open source software licensed under the [LGPL v3 License](https://www.gnu.org/licenses/lgpl.html) by [The Uqbar Foundation](http://www.uqbar-project.org/). Feel free to use it accordingly.