(ns chudao.service.binary
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as bootstrap]
            [ring.util.response :as ring-resp]
            [ring.middleware.multipart-params :as multipart-params]
            [chudao.service.data :as data]
            [chudao.persistence.binary :as persist-binary]))

(defn upload-file
  [request]
  (let [mprequest (multipart-params/multipart-params-request request)
        params (:multipart-params mprequest)]
    (persist-binary/upload-file params))
  (bootstrap/json-response data/upload-success))

(defn download-file
  [request]
  (let [file-name (get-in request [:json-params :file-name])
        data (persist-binary/download-file file-name)]
    (-> (ring-resp/response (:file data))
        (ring-resp/content-type (:content-type data))
        (ring-resp/header "Content-Disposition" (str "attachment; filename=" file-name)))))


