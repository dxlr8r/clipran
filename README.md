# cliptools

CLojure IP TOOLS: Library for creating a list of IPs and more

## Usage

Getting started:

`(require '[cliptools.generate :refer [ip-list]])`

Functions:

* ip-list, will return IPs as a list, examples:
  * rng-str: `(ip-list "192.168.1.1-192.168.1.10")`
  * to from: `(ip-list "192.168.1.1" "192.168.1.10")`

## License

Copyright © 2015 Simen Strange Øya

Distributed under the Modified BSD license
