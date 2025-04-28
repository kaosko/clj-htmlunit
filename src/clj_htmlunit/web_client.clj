(ns clj-htmlunit.web-client
  (:import [org.htmlunit WebClient]
           [java.net URL]))

(defn make-web-client [& {:keys [request-headers
                                 java-script?
                                 redirect?
                                 css?
                                 timeout
                                 disable-logging?]}]
  (let [client (WebClient.)]
    (when request-headers
      (doseq [elem request-headers]
        (.addRequestHeader client (key elem) (val elem))))
    (when (not (= nil redirect?))
      (-> client (.getOptions) (.setRedirectEnabled redirect?)))
    (when (not (= nil timeout))
      (-> client (.getOptions) (.setTimeout timeout)))
    (when java-script?
      (-> client (.getOptions) (.setJavaScriptEnabled java-script?)))
    (when (not (= nil css?))
      (-> client (.getOptions) (.setCssEnabled css?)))
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

(defprotocol IWebClient
  (page 
    [client url]
    [client url wait-for-javascript])
  (close-all [client]))

(extend-type WebClient
  IWebClient
  (page
    ([client ^String url] (.getPage client url))
    ; there's also one with ^URL but maybe String is more useful
    ;([client ^URL url] (.getPage client url))
    ([client url wait-for-javascript]
      (let [result (page client url)]
        (.waitForBackgroundJavaScript client wait-for-javascript)
        result)))
  (close [client] (.close client)))


      
                                

