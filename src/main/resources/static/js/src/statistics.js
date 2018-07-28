jQuery(function($) {
    // Asynchronously Load the map API
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
    var history;
    var linePlanCords = [];

    $.getJSON('http://localhost:8080/test', function(data) {

       $.getJSON('http://localhost:8080/getByBikeNumber?bikeNumber=88594', function(jsonhistory) {
        console.log(jsonhistory.length);
            map = new google.maps.Map(document.getElementById("googleMap"), mapOptions);


           var infoWindow = new google.maps.InfoWindow(), marker, i;
           // console.log(JSON.stringify(jsonhistory[1]));

           for( i = 0; i < data.length; i++ ) {
               for(j = 0; j < jsonhistory.length; j++) {
                   if(data[i].extra.number == jsonhistory[j].stationNumber) {
                       console.log(JSON.stringify(jsonhistory[j]));
                       var position = new google.maps.LatLng(data[i].latitude,data[i].longitude);
                       bounds.extend(position);
                       linePlanCords.push(position);
                       marker = new google.maps.Marker({
                           position: position,
                           map: map,
                           title: data[i].name +" "+jsonhistory[j].date
                       });

                       google.maps.event.addListener(marker, 'click', (function(marker, i, j) {
                           return function() {
                               infoWindow.setContent('<div class="info_content">' +
                                   '<h3>'+data[i].name+'</h3>' +
                                   '<p>'+
                                    'Free bikes: '+data[i].free_bikes+'</br>'+
                                   'Bikes IDs: '+data[i].extra.bike_uids+'</br>'+
                                   '</p>' +
                                   '</div>');
                               infoWindow.open(map, marker);
                           }
                       })(marker, i));
                       map.fitBounds(bounds);
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
};

    document.getElementsByClassName("lodz").onclick = function clicklodz () {
        console.log("click")
    }
