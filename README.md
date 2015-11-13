# Clojure Animation Tower of Hanoi

The code animates Tower of Hanoi in 3 ways

* a terminal,
* a gaphical window and
* a web browser

written in Clojure.

## Demo

[Clojure - Animate Tower of Hanoi in 3 ways](https://www.youtube.com/watch?v=Z1AumosMroU)

## Usage

### on a terminal

```clj
$ lein repl
toh.core=> (ns toh.terminal)
toh.terminal=> (require 'toh.terminal :reload-all)
toh.terminal=> (display 4 50) ; 4 disks, 50 ms wait
toh.terminal=> (display 5 10) ; 5 disks, 10 ms wait
toh.terminal=> (System/exit 0)
```

### on a window

```clj
$ lein repl
toh.core=> (ns toh.swing)
toh.swing=> (require 'toh.swing :reload-all)
toh.swing=> (display 4 50 16) ; 4 disks, 50 ms wait, unit 16 pixels
toh.swing=> (close!)
toh.swing=> (display 5 10  8) ; 5 disks, 10 ms wait, unit 8 pixels
toh.swing=> (close!)
toh.swing=> (System/exit 0)
```

### on a web browser

```clj
$ lein cljsbuild once
$ lein trampoline cljsbuild lein-repl
; (open http://localhost:9000/toh_canvs.html with your web browser)
ClojureScript:cljs.user> (ns toh.canvas)
ClojureScript:toh.canvas> (display 4 50 16) ; 4 disks, 50 ms wait, unit 16 pixels
ClojureScript:toh.canvas> (display 5 10 8) ; 5 disks, 10 ms wait, unit 8 pixels
ClojureScript:toh.canvas> :cljs/quit
```

## License

Copyright (c) 2015 Yoshinori Kohyama. Distributed under the BSD 3-Clause License.
