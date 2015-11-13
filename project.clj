(defproject clj-animation-towerofhanoi "0.1.0-SNAPSHOT"
  :description "Animates Tower of Hanoi in 3 ways"
  :url "http://github.com/kohyama/clj-animation-towerofhanoi"
  :license {:name "BSD 3-Clause License"}
  :repositories {"sonatype-oss-public" ; for core.async
                 "https://oss.sonatype.org/content/groups/public/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 [org.clojure/clojurescript "0.0-1909"]
                 [com.cemerick/clojurescript.test "0.0.4"]]
  :clean-targets ["out" "repl" "target"
                  [:cljsbuild :builds 0 :compiler :output-to]]
  :plugins [[lein-cljsbuild "0.3.3"]]
  :cljsbuild {
    :builds [{
      :source-paths ["cljs-src"]
      :compiler {:output-to "toh_canvas.js"}}]
    :crossovers [toh.core toh.twod]}
  :main toh.core)
