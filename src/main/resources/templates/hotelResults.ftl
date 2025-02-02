{
    "status": {
        "code": "${Status.code}",
        "description": "${Status.description}"
    },
    <#if error??>
    "error": {
        "errorCode": "${error.errorCode}",
        "errorMessage": "${error.errorMessage}"
    }
    <#else>
    "hotelResults": [
        <#list hotelResults as hotel>
        {
            "hotelCode": "${hotel.hotelCode}",
            "currency": "${hotel.currency}",
            "rooms": [
                <#list hotel.rooms as room>
                {
                    "name": "${room.name}",
                    "bookingCode": "${room.bookingCode}",
                    "inclusion": "${room.inclusion}",
                    "dayRates": [
                        <#list room.dayRates as dayRate>
                        {
                            "basePrice": ${dayRate.basePrice}
                        }<#if dayRate_has_next>,</#if>
                        </#list>
                    ],
                    "totalFare": ${room.totalFare},
                    "totalTax": ${room.totalTax},
                    "roomPromotion": [
                        <#if room.roomPromotion?is_sequence>
                            <#list room.roomPromotion as promotion>
                            "${promotion}"<#if promotion_has_next>,</#if>
                            </#list>
                        <#else>
                            "${room.roomPromotion}"
                        </#if>
                    ],
                    "cancellationPolicies": [
                        <#list room.cancelPolicies as cancelPolicy>
                        {
                            "index": "${cancelPolicy.index}",
                            "fromDate": "${cancelPolicy.fromDate}",
                            "chargeType": "${cancelPolicy.chargeType}",
                            "cancellationCharge": ${cancelPolicy.cancellationCharge}
                        }<#if cancelPolicy_has_next>,</#if>
                        </#list>
                    ],
                    "mealType": "${room.mealType}",
                    "isRefundable": ${room.isRefundable},
                    "supplements": [
                        <#list room.supplements as supplement>
                        {
                            "index": ${supplement.index},
                            "type": "${supplement.type}",
                            "description": "${supplement.description}",
                            "price": ${supplement.price},
                            "currency": "${supplement.currency}"
                        }<#if supplement_has_next>,</#if>
                        </#list>
                    ],
                    "withTransfers": ${room.withTransfers}
                }<#if room_has_next>,</#if>
                </#list>
            ]
        }<#if hotel_has_next>,</#if>
        </#list>
    ]
    </#if>
}
