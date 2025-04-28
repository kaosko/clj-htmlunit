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
               ; google.com failed with org.htmlunit.ScriptException: identifier is a reserved word
               ; See https://github.com/HtmlUnit/htmlunit/issues/480
               ; and https://github.com/HtmlUnit/htmlunit/issues/755
                   ;"https://www.google.com"
               "https://www.w3.org")))

;;these tests rely on google staying the same
;;thats a terrible idea
(deftest test-html-page
  (let [page (wc/page (wc/make-web-client
                            :redirect? true
                            :javascript? true
                            :disable-logging? true)
                          ;"https://www.google.com"
                      "https://www.w3.org")]
    (is (hp/document-element page))
    ;(is (he/id (hp/element-by-id page "csi")) "csi")
    (is (he/id (hp/element-by-id page "main")) "main")))
      