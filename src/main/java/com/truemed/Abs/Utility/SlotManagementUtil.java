package com.truemed.Abs.Utility;

import java.time.LocalTime;

import com.truemed.Abs.Constants.appConstants;

public class SlotManagementUtil {
	public static LocalTime getSlotEndTime(LocalTime starttime) {

		return starttime.plusMinutes(appConstants.SLOT_DURATION_IN_MINUTES);
	}
}
