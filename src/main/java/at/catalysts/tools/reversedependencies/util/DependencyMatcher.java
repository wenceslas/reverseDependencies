package at.catalysts.tools.reversedependencies.util;

import java.util.ArrayList;
import java.util.List;

import at.catalysts.tools.reversedependencies.data.Dependency;
import at.catalysts.tools.reversedependencies.data.DependencyTree;

/**
 * Util class for matching dependencies
 * 
 */
public class DependencyMatcher {

    /**
     * match a list of dependencies against reversed dependencies (their used by information)
     * 
     * @param reverseDTs
     * @param matchDependencies
     * @return
     */
    public static List<DependencyTree> matchDependencyQuery(List<DependencyTree> reverseDTs, List<Dependency> matchDependencies) {
        return matchDependencyQuery(reverseDTs, matchDependencies, false);
    }

    public static List<DependencyTree> matchDependencyQuery(List<DependencyTree> reverseDTs, List<Dependency> matchDependencies, boolean matchMajorOnly) {
        List<DependencyTree> found = new ArrayList<>();
        for (DependencyTree revDT : reverseDTs) {
            Dependency revD = revDT.getDependency();
            if (revD.getArtifactId().isEmpty() || revD.getArtifactId().equals("null")
                    || revD.getVersion().isEmpty() || revD.getVersion().equals("null")) {
                continue;
            }
            if (revDT.getUsedBy().isEmpty()) {
                continue;
            }
            Dependency revDUsedBy = revDT.getUsedBy().get(0).getDependency();
            for (Dependency matchD : matchDependencies) {
                if (matchD.getVersion().isEmpty() || matchD.getVersion().equals("null")) {
                    continue;
                }
                if (revDUsedBy.getArtifactId().equals(matchD.getArtifactId())
                        && revDUsedBy.getVersion().equals(matchD.getVersion())) {
                    if (matchMajorOnly) {
                        String majorVersion = getMajorVersion(revDT.getDependency().getVersion());
                        revD.setVersion(majorVersion);
                    }
                    addMatchedDependency(found, revD, revDUsedBy);
                }
            }
        }
        
        return found;
    }

    private static String getMajorVersion(String version) {
        String[] values = null;
        if (version.split("\\.").length > 1) {
            values = version.split("\\.");
        } else if (version.split("-").length > 1) {
            values = version.split("-");
        }
        if (values == null) {
            return version;
        }
        return values[0].trim();
    }

    /**
     * add dependency including usedBy entry to list of dependencyTrees ignoring duplicates
     * 
     * @param matchedDTrees
     * @param revD
     * @param revDUsedBy
     */
    public static void addMatchedDependency(List<DependencyTree> matchedDTrees, Dependency revD, Dependency revDUsedBy) {
        boolean exists = false;
        DependencyTree revDTUsedBy = new DependencyTree();
        revDTUsedBy.setDependency(revDUsedBy);
        for (DependencyTree matchedDT : matchedDTrees) {
            if (matchedDT.getDependency().getArtifactId().equals(revD.getArtifactId())
                    && matchedDT.getDependency().getVersion().equals(revD.getVersion())) {
                if (!isDependencyAlreadyUsed(matchedDTrees, matchedDT.getDependency())) {
                    matchedDT.getUsedBy().add(revDTUsedBy);
                }
                exists = true;
            }
        }
        if (!exists) {
            DependencyTree dt = new DependencyTree();
            dt.setDependency(revD);
            dt.getUsedBy().add(revDTUsedBy);
            matchedDTrees.add(dt);
        }
    }

    private static boolean isDependencyAlreadyUsed(List<DependencyTree> uses, Dependency dep) {
        for (DependencyTree tree : uses) {
            if (tree.getDependency().getArtifactId().equals(dep.getArtifactId())
                && tree.getDependency().getGroupId().equals(dep.getGroupId())
                && tree.getDependency().getVersion().equals(dep.getVersion())) {
                return true;
            }
        }
        return false;
    }

}