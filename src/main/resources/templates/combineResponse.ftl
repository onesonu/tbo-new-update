<#setting number_format="0.##">
{
"HotelResult": [
<#list resultsTbo.hotelResult as hotelTbo>
    <#assign matchedMngo = resultsMngo?filter(hotelMngo -> hotelMngo.hotelCode == hotelTbo.hotelCode) />
    <#if matchedMngo?has_content>
        {
        "hotelName": "${matchedMngo[0].hotelName}",
        "rating": "${matchedMngo[0].rating}",
        "location": "${matchedMngo[0].address}",
        "amenities": [
        <#list matchedMngo[0].hotelFacilities as facility>
        "${facility}"<#if facility_has_next>,</#if>
</#list>
],
"images": [
<#list matchedMngo[0].images as image>
"${image}"<#if image_has_next>,</#if>
</#list>
],
"attractions": [
<#list matchedMngo[0].attractions as attraction>
{
"locationName": "${attraction.locationName}",
"distanceInKm": ${attraction.distanceInKm}
}<#if attraction_has_next>,</#if>
</#list>
],
"hotelDescription": "${matchedMngo[0].description}",
"map": "${matchedMngo[0].map}",
"HotelCode": "${hotelTbo.hotelCode}",
"Currency": "${hotelTbo.currency}",
"Rooms": [
<#list hotelTbo.rooms as room>
{
"Name": [
<#list room.name as roomName>
"${roomName}"<#if roomName_has_next>,</#if>
</#list>
],
"BookingCode": "${room.bookingCode}",
"Inclusion": "${room.inclusion}",
"DayRates": [
<#list room.dayRates as dayRateList>
[
<#list dayRateList as dayRate>
{
"BasePrice": "${dayRate.basePrice}"
}<#if dayRate_has_next>,</#if>
</#list>
]<#if dayRateList_has_next>,</#if>
</#list>
],
"TotalFare": ${room.totalFare},
"TotalTax": ${room.totalTax},
"RoomPromotion": <#if room.roomPromotion??>[<#list room.roomPromotion as promo>"${promo}"<#if promo_has_next>,</#if></#list>]<#else>null</#if>,
"CancelPolicy": <#if room.cancelPolicies??>
[<#list room.cancelPolicies as policy>
{
"Index": "${policy.index!""}",
"FromDate": "${policy.fromDate!""}",
"ChargeType": "${policy.chargeType!""}",
"CancellationCharge": ${policy.cancellationCharge!"0.0"}
}<#if policy_has_next>,</#if>
</#list>]<#else>null</#if>,
"MealType": "${room.mealType}",
"IsRefundable": ${room.refundable?c},
"Supplements": <#if room.supplements??>[<#list room.supplements as suppList>[<#list suppList as supp>{"Index": ${supp.index}, "Type": "${supp.type}", "Description": "${supp.description}", "Price": ${supp.price}, "Currency": "${supp.currency}"}<#if supp_has_next>,</#if></#list>]<#if suppList_has_next>,</#if></#list>]<#else>null</#if>,
"WithTransfers": ${room.withTransfers?string("true", "false")}
}<#if room_has_next>,</#if>
</#list>
]
}<#if hotelTbo_has_next>,</#if>
</#if>
</#list>
]
}
