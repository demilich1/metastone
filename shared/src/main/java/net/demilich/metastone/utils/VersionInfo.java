package net.demilich.metastone.utils;

public class VersionInfo {

	private static final int MAJOR_INDEX = 0;
	private static final int MINOR_INDEX = 1;
	private static final int REVISION_INDEX = 2;
	private static final int RELEASE_INDEX = 3;

	public String version;
	public String[] whatsNew;

	public boolean isNewerVersionAvailable(String localVersionStr) {
		return updateRequired(localVersionStr, version);
		
	}
	
	public static boolean updateRequired(String version1, String version2) {
		Integer[] localVersion = parseVersionString(version1);
		Integer[] remoteVersion = parseVersionString(version2);
		
		if (remoteVersion[MAJOR_INDEX] > localVersion[MAJOR_INDEX]) {
			return true;
		}
		if (remoteVersion[MAJOR_INDEX] == localVersion[MAJOR_INDEX] && remoteVersion[MINOR_INDEX] > localVersion[MINOR_INDEX]) {
			return true;
		}
		if (remoteVersion[MAJOR_INDEX] == localVersion[MAJOR_INDEX] && remoteVersion[MINOR_INDEX] == localVersion[MINOR_INDEX]
				&& remoteVersion[REVISION_INDEX] > localVersion[REVISION_INDEX]) {
			return true;
		}
		if (remoteVersion[MAJOR_INDEX] == localVersion[MAJOR_INDEX] && remoteVersion[MINOR_INDEX] == localVersion[MINOR_INDEX]
				&& remoteVersion[REVISION_INDEX] == localVersion[REVISION_INDEX] && remoteVersion.length == RELEASE_INDEX
				&& localVersion.length > RELEASE_INDEX) {
			return true;
		}
		if (remoteVersion.length > RELEASE_INDEX && localVersion.length > RELEASE_INDEX
				&& remoteVersion[MAJOR_INDEX] == localVersion[MAJOR_INDEX] && remoteVersion[MINOR_INDEX] == localVersion[MINOR_INDEX]
				&& remoteVersion[REVISION_INDEX] == localVersion[REVISION_INDEX] && remoteVersion[RELEASE_INDEX] > localVersion[RELEASE_INDEX]) {
			return true;
		}
		return false;
	}

	private static Integer[] parseVersionString(String rawVersionString) {
		String versionString = rawVersionString.replaceAll("[^\\d+|^\\.RC]", "");
		String[] releaseVersion = versionString.split("RC");
		String[] parts = versionString.split("\\.");
		Integer[] versions;
		if (releaseVersion.length > 1) {
			parts = releaseVersion[0].split("\\.");
			versions = new Integer[parts.length + 1];
			for (int i = 0; i < parts.length; i++) {
				versions[i] = Integer.parseInt(parts[i]);
			}
			versions[RELEASE_INDEX] = Integer.parseInt(releaseVersion[1]);
		} else {
			parts = versionString.split("\\.");
			versions = new Integer[parts.length];
			for (int i = 0; i < versions.length; i++) {
				versions[i] = Integer.parseInt(parts[i]);
			}
		}
		return versions;
	}
}
