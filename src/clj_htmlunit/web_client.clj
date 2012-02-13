(ns clj-htmlunit.web-client
  (:import [com.gargoylesoftware.htmlunit
            WebClient]))

(defn make-web-client [& {:keys [request-headers
                                 java-script?
                                 redirect?
                                 css?
                                 timeout
                                 disable-logging?]}]
  (let [client (WebClient.)]
    (println "making client")
    (when request-headers
      (doseq [elem request-headers]
        (.addRequestHeader client (key elem) (val elem))))
    (when (not (= nil redirect?))
      (.setRedirectEnabled client redirect?))
    (when (not (= nil timeout))
      (.setTimeout client timeout))
    (when java-script?
      (do
      (println "enabled javascript")
      (.setJavaScriptEnabled client java-script?)))
    (when (not (= nil css?))
      (.setCssEnabled client css?))
    (when disable-logging?
      (-> 
        (org.apache.commons.logging.LogFactory/getFactory)
        (.setAttribute "org.apache.commons.logging.Log" "org.apache.commons.logging.impl.NoOpLog"))
      
      (->
        (java.util.logging.Logger/getLogger "com.gargoylesoftware.htmlunit")
        (.setLevel java.util.logging.Level/OFF))
      (->
        (java.util.logging.Logger/getLogger "org.apache.commons.httpclient")
        (.setLevel java.util.logging.Level/OFF)))
    client))


      
                                

