{
"HotelResults": [
<#list results as hotel>
    {
    "Id": "${hotel.id}",
    "HotelCode": "${hotel.hotelCode}",
    "HotelName": "${hotel.hotelName}",
    "Address": "${hotel.address}",
    "CountryName": "${hotel.countryName}",
    "CountryCode": "${hotel.countryCode}",
    "Description": "${hotel.description}",
    "FaxNumber": "${hotel.faxNumber}",
    "Facilities": <#if hotel.hotelFacilities?? && hotel.hotelFacilities?size gt 0>"${hotel.hotelFacilities?join(", ")}", <#else> "N/A", </#if>
    "Map": "${hotel.map}",
    "PhoneNumber": "${hotel.phoneNumber}",
    "PinCode": "${hotel.pinCode}",
    "WebsiteUrl": <#if hotel.hotelWebsiteUrl?? && hotel.hotelWebsiteUrl?has_content>"${hotel.hotelWebsiteUrl}", <#else> "N/A", </#if>
    "CityName": "${hotel.cityName}",
    "CreateDate": <#if hotel.createdDate?? && hotel.createdDate?has_content>"${hotel.createdDate}", <#else> "N/A",</#if>
    "UpdateDate": <#if hotel.updatedDate?? && hotel.updatedDate?has_content>"${hotel.updatedDate}", <#else> "N/A",</#if>
    "Images": [
    <#if hotel.images?? && hotel.images?size gt 0>
        <#list hotel.images as image>
            "${image}"<#if image_has_next>,</#if>
        </#list>
    </#if>
    ],
    "Rating": "${hotel.rating}",
    "Attractions": [
    <#if hotel.attractions?? && hotel.attractions?size gt 0>
        <#list hotel.attractions as attraction>
            {
            "Location": "${attraction.location!''}",
            "DistanceInKm": ${attraction.distanceInKm!'0'}
            }<#if attraction_has_next>,</#if>
        </#list>
    </#if>
    ]
    }<#if hotel_has_next>,</#if>
</#list>
]
}
