{
    "HotelResults": [
        <#list hotelIds as hotelId >{"HotelId": "${hotelId}"}</#list>
        <#list hotelName as hotelName >{"HotelName": "${hotelName}"}</#list>
        "countryName" : "${countryNames}",


    ]
}
