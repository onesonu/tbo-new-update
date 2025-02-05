{
    "HotelResults": [
        <#list HotelResults as hotel>
        {
            "Id": "${hotel.id}",
            "HotelCode": "${hotel.hotelCode}",
            "HotelName": "${hotel.hotelName}",
            "Address": "${hotel.address}",
            "CountryName": "${hotel.countryName}",
            "CountryCode": "${hotel.countryCode}",
            "Description": "${hotel.description}",
            "FaxNumber": "${hotel.faxNumber}",
            "Facilities": "${hotel.facilities}",
            "Map": "${hotel.map}",
            "PhoneNumber": "${hotel.phoneNumber}",
            "PinCode": "${hotel.pinCode}",
            "WebsiteUrl": "${hotel.websiteUrl}",
            "CityName": "${hotel.cityName}",
            "CreateDate": "${hotel.createDate}",
            "UpdateDate": "${hotel.updateDate}",
            "Images": "${hotel.images}",
            "Rating": "${hotel.rating}",
            "Attractions": [
                <#list hotel.attractions as attraction>
                {
                    "Location": "${attraction.location}",
                    "DistanceInKm": ${attraction.distanceInKm}
                }<#if attraction_has_next>,</#if>
                </#list>
            ]
        }<#if hotel_has_next>,</#if>
        </#list>
    ]
}
