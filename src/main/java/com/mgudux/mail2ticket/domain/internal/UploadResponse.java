package com.mgudux.mail2ticket.domain.internal;

import java.util.UUID;

// after successful analyze, we create three objects.
// This stores the IDs of the three objects to show it to the frontend.
public record UploadResponse(
        UUID customerId,
        UUID emlFileId,
        UUID ticketId
) {
}
