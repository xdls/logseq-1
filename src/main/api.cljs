(ns ^:no-doc api
  (:require [frontend.db :as db]
            [frontend.db.query-dsl :as dsl]
            [frontend.state :as state]
            [datascript.core :as d]
            [cljs.reader]))

(defn ^:export q
  [s]
  (when-let [result (dsl/query s)]
    @result))

(defn ^:export query
  "Datascript query."
  [query & inputs]
  (when-let [repo (state/get-current-repo)]
    (when-let [conn (db/get-conn repo)]
      (let [query (cljs.reader/read-string query)
            result (apply d/q query conn inputs)]
        (clj->js result)))))

(defn ^:export custom_query
  [s]
  (when-let [result (db/custom-query s)]
    @result))
