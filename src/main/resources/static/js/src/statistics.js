jQuery(function($) {
    var script = document.createElement('script');
    script.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyD0YE_P_PHJTIyvx0Xc3lt9Yd752EyMqhk&callback=initialize";
    document.body.appendChild(script);
});
function initialize() {
    var map;
    var bounds = new google.maps.LatLngBounds();
    var mapOptions = {
        mapTypeId: 'roadmap'
    };
    var bikeList = [];
    var from="";
    var to="";
    $.getJSON("http://localhost:8080/getAllBikes", function(json){
        bikeList = json;
        var str ="";

        for(i=0;i<bikeList.length;i++){
             var li=document.createElement('li')
            li.className = "clickMe"
            li.innerHTML='<a href="#">'+bikeList[i]+'</a>'
            document.getElementById("bikes").appendChild(li)

        }

        $(function () {
            $('.clickMe').click(function () {

                var str = $(this).text();
                console.log("History for:" + str);
                console.log(bikeList);
                var linePlanCords = [];
                $.getJSON('http://localhost:8080/allStations', function(data) {

                    $.getJSON('http://localhost:8080/getByBikeNumber?bikeNumber='+str, function(jsonhistory) {
                        map = new google.maps.Map(document.getElementById("googleMap"), mapOptions);
                        var infoWindow = new google.maps.InfoWindow(), marker, i;
                        for(k=0;k<jsonhistory.length;k++){

                            console.log("history:" + JSON.stringify(jsonhistory[k]));
                        }

                        for(k=0;k<data.length;k++){

                            console.log("DATA:" + JSON.stringify(data[k]));
                        }

                        for( i = 0; i < data.length; i++ ) {
                            for(j = 0; j < jsonhistory.length; j++) {
                                if(data[i].stationNumber == jsonhistory[j].stationNumber) {

                                    var d1 = from.split("-");
                                    var d2 = to.split("-");
                                    var cc = jsonhistory[j].date.substring(0, 10).split("-");

                                    var f = new Date(d1[0], parseInt(d1[1]) - 1, d1[2]);
                                    var t = new Date(d2[0], parseInt(d2[1]) - 1, d2[2]);
                                    var c = new Date(cc[0], parseInt(cc[1]) - 1, cc[2]);

                                    if(c > f && c < t) {

                                        console.log(JSON.stringify(data[i].latitude));
                                        var lat = data[i].latitude;
                                        var long = data[i].longtitude;
                                        var position = new google.maps.LatLng(lat, long);
                                        bounds.extend(position);
                                        linePlanCords.push(position);
                                        marker = new google.maps.Marker({
                                            position: position,
                                            map: map,
                                            title: data[i].stationName + " " + jsonhistory[j].date
                                        });

                                        google.maps.event.addListener(marker, 'click', (function (marker, i, j) {
                                            return function () {
                                                infoWindow.setContent('<div class="info_content">' +
                                                    '<h3>' + data[i].stationName + '</h3>' +

                                                    '</div>');
                                                infoWindow.open(map, marker);
                                            }
                                        })(marker, i));
                                        map.fitBounds(bounds);
                                    };
                                };

                            }

                        }
                        var flightPath = new google.maps.Polyline({
                            path: linePlanCords,
                            geodesic: true,
                            strokeColor: '#FF0000',
                            strokeOpacity: 1.0,
                            strokeWeight: 2
                        });


                        flightPath.setMap(map);
                        var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
                            this.setZoom(14);
                            google.maps.event.removeListener(boundsListener);
                        });

                        console.log(data.length);

                    });
                });
            });
        });
    });

    $(function() {
        $('input[name="daterange"]').daterangepicker({
            opens: 'left'
        }, function(start, end, label) {
            console.log("A new date selection was made: " + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
            from = start.format('YYYY-MM-DD');
            to = end.format('YYYY-MM-DD');
        });
    });
};
