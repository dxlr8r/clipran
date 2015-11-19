# cliptools

CLojure IP TOOLS: Library for doing mathematical operations to an IP address and creating lists

## Why

Do you want to manipulate IP addresses in Clojure? This library does mathematical operations to the IP, there is no string manipulation involved.

## Installation

I plan to host this on clojars in the future. But for now I would recommend installing it through [localrepo](https://github.com/kumarshantanu/lein-localrepo). First download the source code, then cd into the source and execute: `lein clean ; lein jar ; lein localrepo install target/*.jar no.ifixit/cliptools "1.0.0"`

Then you can require the library into clojure as you would any other library:

`(require '[cliptools.calculate :refer [ip-math-operation ip->int int->ip]]
          '[cliptools.generate :refer [ip-list]])`

## Usage

* cliptools.calculate, library for IP calculation:
  * ip->int, converts an IP to int, example:
    * `(ip->int "10.0.64.128")`, returns `167788672`
  * int->ip, converts an int to an IP, example:
    * (int->ip (+ (ip->int "10.0.192.128") (ip->int "10.0.64.128"))), returns `"20.1.1.0"`
  * ip-math-operation, does an operation to an IP address, example:
    * `(ip-math-operation + "10.0.192.128" 167788672)`, returns `"20.1.1.0"`
* cliptools.validate, library for validation of input.
* cliptools.generate, combines calculate and validate, contains one function:
  * ip-list, will return IPs as a list, examples:
    * rng-str: `(ip-list "192.168.1.1-192.168.1.10")`
    * to from: `(ip-list "192.168.1.1" "192.168.1.10")`
      * Both will return: `("192.168.1.1" "192.168.1.2" "192.168.1.3" "192.168.1.4" "192.168.1.5" "192.168.1.6" "192.168.1.7" "192.168.1.8" "192.168.1.9" "192.168.1.10")`

## License

Copyright © 2015 Simen Strange Øya

Distributed under the Modified BSD license
