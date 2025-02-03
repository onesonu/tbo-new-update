{
    "Status": {
        "Code": "${Status.code}",
        "Description": "${Status.description}"
    },
    <#if error??>
    "error": {
        "errorCode": "${error.errorCode}",
        "errorMessage": "${error.errorMessage}"
    }
    <#else>
    "HotelResults": [
        <#list hotelResults as hotel>
        {
            "HotelCode": "${hotel.hotelCode}",
            "Currency": "${hotel.currency}",
            "Rooms": [
                <#list hotel.rooms as room>
                {
                    "Name": "${room.name}",
                    "BookingCode": "${room.bookingCode}",
                    "Inclusion": "${room.inclusion}",
                    "DayRates": [
                        <#list room.dayRates as dayRate>
                        {
                            "BasePrice": ${dayRate.basePrice}
                        }<#if dayRate_has_next>,</#if>
                        </#list>
                    ],
                    "TotalFare": ${room.totalFare},
                    "TotalTax": ${room.totalTax},
                    "RoomPromotion": [
                        <#if room.roomPromotion?is_sequence>
                            <#list room.roomPromotion as promotion>
                            "${promotion}"<#if promotion_has_next>,</#if>
                            </#list>
                        <#else>
                            "${room.roomPromotion}"
                        </#if>
                    ],
                    "CancellationPolicies": [
                        <#list room.cancelPolicies as cancelPolicy>
                        {
                            "Index": "${cancelPolicy.index}",
                            "FromDate": "${cancelPolicy.fromDate}",
                            "ChargeType": "${cancelPolicy.chargeType}",
                            "CancellationCharge": ${cancelPolicy.cancellationCharge}
                        }<#if cancelPolicy_has_next>,</#if>
                        </#list>
                    ],
                    "MealType": "${room.mealType}",
                    "IsRefundable": ${room.isRefundable},
                    "Supplements": [
                        <#list room.supplements as supplement>
                        {
                            "Index": ${supplement.index},
                            "Type": "${supplement.type}",
                            "Description": "${supplement.description}",
                            "Price": ${supplement.price},
                            "Currency": "${supplement.currency}"
                        }<#if supplement_has_next>,</#if>
                        </#list>
                    ],
                    "WithTransfers": ${room.withTransfers}
                }<#if room_has_next>,</#if>
                </#list>
            ]
        }<#if hotel_has_next>,</#if>
        </#list>
    ]
    </#if>
}
