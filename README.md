# xml_test

  Playing around with github cr workflows
This is my first clojure application, written mostly for the experience of clojure, 
and as part of a programming test. In my estimate it would have gone much better had 
I actually known the programming language before-hand, and though with poor performance, 
it does get the job at hand done. 

Before starting this late on a Sunday afternoon, I had spent six hours debugging a C/C++
memory leak, and completely replacing the JSON library being used across a multi-tiered system.
This took roughly the same amount of time, and the following are articles I depended heavily upon
during the process:
 http://blog.korny.info/2014/03/08/xml-for-fun-and-profit.html
 http://clojure-doc.org/articles/tutorials/parsing_xml_with_zippers.html
 http://writingcoding.blogspot.co.uk/2008/06/clojure-series-table-of-contents.html

Please do note, it was during this same time too, that I worked through most of Volkmann's Clojure
article (http://java.ociweb.com/mark/clojure/article.html) as well as some of Eric Rochester's Clojure 
series (http://writingcoding.blogspot.co.uk/2008/06/clojure-series-table-of-contents.html)

All of my testing was done using lein repl, and I have left the intermediate functions I used to test
commented out in the source file. There may or may not be a few simple ways to get performance improved, 
perhaps something other than "contains", or applying the filter before the serialization. I would add some
automated testing in Python, but a better use of my time would be to continue working through some tutorials,
and perhaps a book.

## Installation

The Wikipedia XML file is assumed to be of the name "enwiki-latest-abstract23.xml" and to reside in the 
same directory as this readme file. Being that its path has been hard-coded, should it be of different name
or path, that can be changed by updating the value of the 'xmlFilePath' variable within the core.clj source file.

## Usage

lein deps
lein run

## Options

No available options

## Examples

Use one's browser to browse to http://localhost?search?q=Johannesburg

### Bugs

Takes about 1 minute to return all of the results

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
