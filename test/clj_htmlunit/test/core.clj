(ns clj-htmlunit.test.core
  (:require [clj-htmlunit.html-element :as he])
  (:require [clj-htmlunit.html-page :as hp])
  (:require [clj-htmlunit.web-client :as wc])
  (:use [clojure.test]))

(deftest test-web-client
  (is (wc/make-web-client 
        :redirect? true 
        :css? true) 
      "creation of webclient failed"))

(deftest test-get-html-page
  (is (wc/page (wc/make-web-client
                     :redirect? true
                     :disable-logging? true)
                   "http://www.google.com")))

;;these tests rely on google staying the same
;;thats a terrible idea
(deftest test-html-page
  (let [page (wc/page (wc/make-web-client
                            :redirect? true
                            :javascript? true
                            :disable-logging? true)
                          "http://www.google.com")]
    (is (hp/document-element page))
    (is (he/id (hp/element-by-id page "csi")) "csi")))
      