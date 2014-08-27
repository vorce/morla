(ns morla.core
  (:require [clojure.java.io :as io])
  (:gen-class :main true))

(def separator "/")
(def home-dir ["Users", "joel.carlbark"])
(def built-in-commands ["cd", "pwd", "ls", "clear", "quit"])

(defn to-fs [path]
  (clojure.string/join separator (concat [""] path)))

(defn from-fs [strpath]
  (vec (rest (clojure.string/split strpath (re-pattern separator)))))

(def current-dir (atom (io/file (to-fs home-dir))))

(defn pwd []
  (.getAbsolutePath @current-dir))

(defn ls-raw
  ([] (.listFiles @current-dir))
  ([arg] (.listFiles arg)))

(defn ls-format [files]
  (for [f files] (if (.isDirectory f)
                (str (.getName f) separator "\n")
                (str (.getName f) "\n"))))

(defn ls [& args]
  (let [fs (-> (ls-raw @current-dir) (ls-format))]
    fs))

(defn absolute-path?
  "Is the specified path absolute or not"
  [path]
  (= \/ (first path)))

(defn updir? [path]
  (= ".." path))

(defn- level-up []
  (-> (from-fs (pwd)) (drop-last) (to-fs)))

(defn- cd-raw [to]
  (cond
    (absolute-path? to)
      (reset! current-dir (io/file to))
    (updir? to)
      (reset! current-dir (io/file (level-up)))
    :else (swap! current-dir io/file to)))

(defn cd
  ([] (str "Changed dir to home dir: " (cd-raw (to-fs home-dir))))
  ([to] (str "Changed dir to: " (cd-raw to))))

(defn- tokens [input]
  (clojure.string/split input #" "))

(defn built-in? [cmdtoks]
  (not (nil? (some #{(first cmdtoks)} built-in-commands))))

(defn- lispify [tokens]
  (let [base (str "(" (first tokens))]
    (if (empty? (rest tokens))
      (str base ")")
      (str base " \"" (clojure.string/join " " (rest tokens)) "\")"))))

(defn quit []
  (do
    (println "Bye for now!")
    (System/exit 0)))

(defn- prompt []
  (do
    (print (str (pwd) " λ "))
    (read-line)))

(defn- repl []
  (let [inp (read-line)
        toks (tokens inp)
        lisp-inp (lispify toks)]
    (do
      ;(println lisp-inp)
      (binding [*ns* (find-ns 'morla.core)]
        (println (load-string (if (built-in? toks) lisp-inp inp))))
      (recur))))

(defn -main
  "Morla!"
  [& args]
  (do
    (println "Welcome to Morla! A clojure shell prototype.")
    (println "Available commands: pwd, cd, ls, quit")
    (repl)))
