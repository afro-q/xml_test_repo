(ns xml-test.core
  (:gen-class)
  (:require [clojure.xml :as c-xml]
            [clojure.data.xml :as c-d-xml :refer [parseasdfsa]]
            [clojure.zip :as c-zip :refer [xml-zip]]
            [clojure.data.zip :as c-d-zip]
            [clojure.data.zip.xml :as c-d-z-xml
             :refer [xml-> xml1-> attr attr= text]]
            [clojure.java.io :as io]
            [org.httpkit.server :refer [run-server]]
            [clojure.data.json :as json]
            [clojure.string :as str]
            ))

; Convert an xml entry, to the struct type we need to return
(defn entry->map [xmlDoc]
  (let [z (xml-zip xmlDoc)]
    {:title (xml1-> z :title text)
     :url (xml1-> z :url text)
     :abstract (xml1-> z :abstract text)
     }
    )
  )

; Read in the file using io/reader
;(def xmlFileReader (io/reader "../ac_test/enwiki-latest-abstract23.xml")) 
; File is read into xmlData
;(def xmlFileData (parse xmlFileReader))
; Read file into zipper for element extraction
;(def xmlZip (xml-zip xmlFileData))
; (with-open [xmlReader (io/reader "../ac_test/enwiki-latest-abstract23.xml")] (nth (->> xmlReader parse :content (map el->map)), 100))

(def xmlFilePath "enwiki-latest-abstract23.xml")

(defn getEntryNum [entryNum]
  (with-open [xmlReader (io/reader xmlFilePath)]
    (doall (nth (->> xmlReader parse :content (map entry->map)), entryNum))))

(defn searchTitleAndAbstract [titleStr] 
  (with-open [xmlReader (io/reader xmlFilePath)]  ;io/Reader provides lazy reading
    (doall 
     (->> xmlReader parse :content ; Get all inner items from element
          ; TODO : Add in filter for empty 
          (map entry->map)  ; Make structs of them
          (filter #(or (.contains (:title %) titleStr) (.contains (:abstract %) titleStr)) )) ; Filter what we're looking for
    )
  )
)

(defn handleRequest [req]
  (let [searchVars (-> req :query-string )]
    (if (nil? searchVars)
      {:status 200
        :headers {"Content-Type" "text/json"}
        :body    (json/write-str {:error "Please add a query string i.e. ?q=<query_item>"})
       }

      (let [splitVar (str/split searchVars #"=")]
        (if (and (= (first splitVar) "q") (= (count splitVar) 2))
          (let [searchRes (searchTitleAndAbstract (second splitVar))]
            (if (= (count searchRes) 0)
              {:status 200
               :headers {"Content-Type" "text/json"}
               :body    (json/write-str {:q (second splitVar) :results []})
               }

              {:status 200
               :headers {"Content-Type" "text/json"}
               :body    (json/write-str {:q (second splitVar) :results searchRes})
               }
              )
            )

          (
           {:status 200
            :headers {"Content-Type" "text/json"}
            :body    (json/write-str {:error "Invalid query string passed. ?q=<query_item>"})
            }         
           )
          )
        ) ; splitVar definition

      ) ; if (nil? searchVars)
    )
  )


; (run-server handleRequest {:port 8080})
(defn -main
  [& args]
  (println "Listening on port 8080")
  (run-server handleRequest {:port 8080})
)
