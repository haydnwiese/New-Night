package com.example.nightclubpicker.common.list_items;

import com.example.nightclubpicker.common.adapters.Type;

public class OpenHoursListItem implements ListItem {

    private boolean isOpen;
    private String[] weekdayText;
    private boolean isExpanded = false;

    public boolean isOpen() {
        return isOpen;
    }

    public String[] getWeekdayText() {
        return weekdayText;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public Type getType() {
        return Type.OPEN_HOURS_LIST_ITEM;
    }

    public static class Builder {
        private boolean isOpen;
        private String[] weekdayText;

        public Builder setOpen(boolean open) {
            isOpen = open;
            return this;
        }

        public Builder setWeekdayText(String[] weekdayText) {
            this.weekdayText = weekdayText;
            return this;
        }

        public OpenHoursListItem build() {
            OpenHoursListItem listItem = new OpenHoursListItem();
            listItem.isOpen = isOpen;
            listItem.weekdayText = weekdayText;
            return listItem;
        }
    }
}
