package org.wikipedia.notifications;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.wikipedia.json.GsonUtil;
import org.wikipedia.util.DateUtil;
import org.wikipedia.util.log.L;
import org.wikipedia.wikidata.EntityClient;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Notification {
    public static final String TYPE_EDIT_USER_TALK = "edit-user-talk";
    public static final String TYPE_REVERTED = "reverted";
    public static final String TYPE_EDIT_THANK = "edit-thank";
    public static final String TYPE_WELCOME = "welcome";
    public static final String TYPE_EDIT_MILESTONE = "thank-you-edit";
    public static final String TYPE_LOGIN_SUCCESS = "login-success";
    public static final String TYPE_LOGIN_FAIL_NEW = "login-fail-new";
    public static final String TYPE_LOGIN_FAIL_KNOWN = "login-fail-known";
    public static final String TYPE_FOREIGN = "foreign";

    @SuppressWarnings("unused,NullableProblems") @Nullable private String wiki;
    @SuppressWarnings("unused") private long id;
    @SuppressWarnings("unused,NullableProblems") @Nullable private String type;
    @SuppressWarnings("unused,NullableProblems") @Nullable private String category;
    @SuppressWarnings("unused") private long revid;

    @SuppressWarnings("unused,NullableProblems") @Nullable private Title title;
    @SuppressWarnings("unused,NullableProblems") @Nullable private Agent agent;
    @SuppressWarnings("unused,NullableProblems") @Nullable private Timestamp timestamp;
    @SuppressWarnings("unused,NullableProblems") @SerializedName("*") @Nullable private Contents contents;
    @SuppressWarnings("unused,NullableProblems") @Nullable private Map<String, Source> sources;

    @NonNull public String wiki() {
        return StringUtils.defaultString(wiki);
    }

    public long id() {
        return id;
    }

    @NonNull public String type() {
        return StringUtils.defaultString(type);
    }

    @Nullable public Agent agent() {
        return agent;
    }

    @Nullable public Title title() {
        return title;
    }

    public long revID() {
        return revid;
    }

    @Nullable Contents getContents() {
        return contents;
    }

    @NonNull Date getTimestamp() {
        return timestamp != null ? timestamp.date() : new Date();
    }

    @NonNull String getUtcIso8601() {
        return StringUtils.defaultString(timestamp != null ? timestamp.utciso8601 : null);
    }

    @Nullable Map<String, Source> getSources() {
        return sources;
    }

    public boolean isFromWikidata() {
        return wiki().equals(EntityClient.WIKIDATA_WIKI);
    }

    @Override public String toString() {
        return Long.toString(id);
    }

    public static class Title {
        @SuppressWarnings("unused,NullableProblems") @Nullable private String full;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String text;
        @SuppressWarnings("unused") @Nullable private String namespace;
        @SuppressWarnings("unused") @SerializedName("namespace-key") private int namespaceKey;

        @NonNull public String text() {
            return StringUtils.defaultString(text);
        }

        @NonNull public String full() {
            return StringUtils.defaultString(full);
        }

        public boolean isMainNamespace() {
            return namespaceKey == 0;
        }

        public void setFull(@NonNull String title) {
            full = title;
        }
    }

    public static class Agent {
        @SuppressWarnings("unused") private int id;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String name;

        @NonNull public String name() {
            return StringUtils.defaultString(name);
        }
    }

    public static class Timestamp {
        @SuppressWarnings("unused,NullableProblems") @Nullable private String utciso8601;

        public Date date() {
            try {
                return DateUtil.getIso8601DateFormat().parse(utciso8601);
            } catch (ParseException e) {
                L.e(e);
                return new Date();
            }
        }
    }

    public static class Link {
        @SuppressWarnings("unused,NullableProblems") @Nullable private String url;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String label;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String tooltip;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String description;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String icon;

        @NonNull public String getUrl() {
            return StringUtils.defaultString(url);
        }

        @NonNull public String getTooltip() {
            return StringUtils.defaultString(tooltip);
        }

        @NonNull public String getLabel() {
            return StringUtils.defaultString(label);
        }

        @NonNull public String getIcon() {
            return StringUtils.defaultString(icon);
        }
    }

    public static class Links {
        @SuppressWarnings("unused,NullableProblems") @Nullable private JsonElement primary;
        @SuppressWarnings("unused,NullableProblems") @Nullable private List<Link> secondary;
        private Link primaryLink;

        @Nullable public Link getPrimary() {
            if (primary == null) {
                return null;
            }
            if (primaryLink == null && primary instanceof JsonObject) {
                primaryLink = GsonUtil.getDefaultGson().fromJson(primary, Link.class);
            }
            return primaryLink;
        }

        @Nullable public List<Link> getSecondary() {
            return secondary;
        }
    }

    public static class Source {
        @SuppressWarnings("unused,NullableProblems") @Nullable private String title;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String url;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String base;

        @NonNull public String getTitle() {
            return StringUtils.defaultString(title);
        }

        @NonNull public String getUrl() {
            return StringUtils.defaultString(url);
        }

        @NonNull public String getBase() {
            return StringUtils.defaultString(base);
        }
    }

    public static class Contents {
        @SuppressWarnings("unused,NullableProblems") @Nullable private String header;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String compactHeader;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String body;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String icon;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String iconUrl;
        @SuppressWarnings("unused,NullableProblems") @Nullable private Links links;

        @NonNull public String getHeader() {
            return StringUtils.defaultString(header);
        }

        @NonNull public String getCompactHeader() {
            return StringUtils.defaultString(compactHeader);
        }

        @NonNull public String getBody() {
            return StringUtils.defaultString(body);
        }

        @NonNull public String getIconUrl() {
            return StringUtils.defaultString(iconUrl);
        }

        @Nullable public Links getLinks() {
            return links;
        }
    }

    public static class UnreadNotificationWikiItem {
        @SuppressWarnings("unused") private int totalCount;
        @SuppressWarnings("unused,NullableProblems") @Nullable private Source source;

        public int getTotalCount() {
            return totalCount;
        }

        @Nullable public Source getSource() {
            return source;
        }
    }

    public static class SeenTime {
        @SuppressWarnings("unused,NullableProblems") @Nullable private String alert;
        @SuppressWarnings("unused,NullableProblems") @Nullable private String message;

        @Nullable public String getAlert() {
            return alert;
        }

        @Nullable public String getMessage() {
            return message;
        }
    }
}
