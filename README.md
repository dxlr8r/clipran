# cliptools

CLojure IP TOOLS: Library for doing mathematical operations to an IP address and creating lists

## Usage

Getting started:

`(require '[cliptools.calculate :refer [ip-math-operation]]
          '[cliptools.generate :refer [ip-list]])`

Functions:

* ip-math-operation, does an operation to an IP address, example:
  * `(ip-math-operation + "192.168.1.1" 1)`, returns "192.168.1.2"
* ip-list, will return IPs as a list, examples:
  * rng-str: `(ip-list "192.168.1.1-192.168.1.10")`
  * to from: `(ip-list "192.168.1.1" "192.168.1.10")`

## License

Copyright © 2015 Simen Strange Øya

Distributed under the Modified BSD license
