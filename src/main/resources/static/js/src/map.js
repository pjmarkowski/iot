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

    $.getJSON('http://localhost:8080/test', function(data) {

        map = new google.maps.Map(document.getElementById("googleMap"), mapOptions);

        var infoWindow = new google.maps.InfoWindow(), marker, i;

        for( i = 0; i < data.length; i++ ) {
            var position = new google.maps.LatLng(data[i].latitude,data[i].longitude);
            bounds.extend(position);
            marker = new google.maps.Marker({
                position: position,
                map: map,
                title: data[i].name
            });

            google.maps.event.addListener(marker, 'click', (function(marker, i) {
                return function() {
                    infoWindow.setContent('<div class="info_content">' +
                        '<h3>'+data[i].name+'</h3>' +
                        '<p>'+
                        'Empty slots: '+data[i].empty_slots+'</br>'+
                        'Free bikes: '+data[i].free_bikes+'</br>'+
                        'Bikes IDs: '+data[i].extra.bike_uids+'</br>'+
                        '</p>' +
                        '</div>');
                    infoWindow.open(map, marker);
                }
            })(marker, i));
            map.fitBounds(bounds);
        }

        var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
            this.setZoom(14);
            google.maps.event.removeListener(boundsListener);
        });
    });
};

