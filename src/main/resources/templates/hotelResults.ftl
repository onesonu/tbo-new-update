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
                    "totalFare": ${room.totalFare},
                    "totalTax": ${room.totalTax},
                    "mealType": "${room.mealType}",
                    "isRefundable": ${room.isRefundable}
                }<#if room_has_next>,</#if>
                </#list>
            ]
        }<#if hotel_has_next>,</#if>
        </#list>
    ]
    </#if>
}
