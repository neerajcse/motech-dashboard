<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <!-- The following is required by dojo to initialize the cometd setup -->
    <script data-dojo-config="async: true, tlmSiblingOfDojo: true, deps: ['application.js']"
            src="${pageContext.request.contextPath}/dojo/dojo.js.uncompressed.js"></script>
   
    <script type="text/javascript">
        var config = {
            contextPath: '${pageContext.request.contextPath}'
        };
    </script>
    <!-- After this, everything is controlled by angularjs -->
    <title>MOTECH Dashboard - Angular JS</title>
</head>
<body>
  <div ng-app="">
  
    <h2>Dashboard using cometd and angularjs with activemq and tomcat integration</h2>
    <div id="status"></div>
    <div id="stocks"></div>
    <div style="display:none">
    <input id="fbk" type="text" ng-model="data.fbk"></input>
    <input id="ggl" type="text" ng-model="data.ggl"></input>
    </div>
    
    <h1>{{"Facebook: " +data.fbk}}</h1>
    <h1>{{"Google: " +data.ggl}}</h1>
    
  </div>

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.3/angular.min.js"></script>
</body>
</html>
