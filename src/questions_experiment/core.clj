(ns questions-experiment.core
  (:require [clojure.java.io :as io])
  (:import [com.aliasi.chunk Chunker Chunking]
           [com.aliasi.util AbstractExternalizable]))

(defn load-chunker
  [path]
  (AbstractExternalizable/readObject
   (io/as-file path)))

(defn load-questions
  [path]
  (clojure.string/split-lines
   (slurp path)))

(defn tag-questions
  [questions-file chunker-file]
  (let [chunker (load-chunker chunker-file)
        questions (load-questions questions-file)

        chunked
        (map
         (fn [q]
           (.chunk chunker q))
         questions)]

    (map
     (fn [c]
       (let [cset (.chunkSet c)]
         (map
          (fn [s]
            (.start s))
          cset)))
     chunked)))
