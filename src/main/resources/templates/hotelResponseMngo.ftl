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
    "Facilities": <#if hotel.facilities?? && hotel.facilities?size gt 0>
    "${hotel.facilities}",</#if>
    "Map": "${hotel.map}",
    "PhoneNumber": "${hotel.phoneNumber}",
    "PinCode": "${hotel.pinCode}",
    "WebsiteUrl": <#if hotel.websiteUrl?? && hotel.websiteUrl?size gt 0>
    "${hotel.websiteUrl}", </#if>
    "CityName": "${hotel.cityName}",
    "CreateDate": <#if hotel.createDate?? && hotel.createDate?size gt 0>"${hotel.createDate}",</#if>
    "UpdateDate": <#if hotel.updateDate?? && hotel.updateDate?size gt 0>"${hotel.updateDate}",</#if>
    "Images": [
    <#if hotel.images?? && hotel.images?size gt 0>
        <#list hotel.images as image>
            "${image}"<#if image_has_next>,</#if>
        </#list>
    </#if>
    ]
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
