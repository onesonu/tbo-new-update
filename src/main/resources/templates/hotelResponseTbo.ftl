<#setting number_format="0.##">
{
"Status": {
"Code": "${status.code}",
"Description": "${status.description}"
},
<#if hotelResult?? && hotelResult?size gt 0>
    "HotelResults": [
    <#list hotelResult as hotel>
        {
        "HotelCode": "${hotel.hotelCode}",
        "Currency": "${hotel.currency}",
        "Rooms": [
        <#list hotel.rooms as room>
            {
            <#list room.name as name>
                "Name": ["${name}"],
            </#list>
            "BookingCode": "${room.bookingCode}",
            "Inclusion": "${room.inclusion}",
            "DayRates": [
            <#if room.dayRates??>
                <#list room.dayRates as dayRateList>
                    <#list dayRateList as dayRate>
                        {
                        "BasePrice": ${dayRate.basePrice}
                        }<#if dayRate?has_next>,</#if>
                    </#list>
                </#list>
            </#if>
            ],
            "TotalFare": ${room.totalFare},
            "TotalTax": ${room.totalTax},
            "RoomPromotion": [
            <#if room.roomPromotion?? && room.roomPromotion?size gt 0>
                <#list room.roomPromotion as promotion>
                    "${promotion}" <#if promotion?has_next>,</#if>
                </#list>
            </#if>
            ],
            "CancellationPolicies": [
            <#if room.cancelPolicies?? && room.cancelPolicies?size gt 0>
                <#list room.cancelPolicies as cancelPolicy>
                    {
                    "Index": "${cancelPolicy.index!'N/A'}",
                    "FromDate": "${cancelPolicy.fromDate!'N/A'}",
                    "ChargeType": "${cancelPolicy.chargeType!'N/A'}",
                    "CancellationCharge": "${cancelPolicy.cancellationCharge!0.0}"
                    }<#if cancelPolicy?has_next>,</#if>
                </#list>
            </#if>
            ],
            "MealType": "${room.mealType}",
            "IsRefundable": <#if room.refundable?? && room.refundable == true>true<#else>false</#if>,
            "Supplements": [
            <#if room.supplements?? && room.supplements?size gt 0>
                <#list room.supplements as supplementList>
                    <#list supplementList as supplement>
                        {
                        "Index": "${supplement.index}",
                        "Type": "${supplement.type}",
                        "Description": "${supplement.description}",
                        "Price": "${supplement.price}",
                        "Currency": "${supplement.currency}"
                        } <#if supplement?has_next>,</#if>
                    </#list>
                </#list>
            </#if>
            ],
            "WithTransfers": <#if room.withTransfers?? && room.withTransfers == true>true<#else>false</#if>
            }
            <#if room?has_next>,</#if>
        </#list>
        ]
        }
        <#if hotel?has_next>,</#if>
    </#list>
    ]
</#if>
}
