package com.pheenixm.betterbeacons;

import java.util.List;
import com.pheenixm.betterbeacons.BetterBeacons;

public interface IBeaconStorage {
    public boolean contains(BetterBeacons beacon);
    public BetterBeacons get(UUID worldUuid, int x, int y, int z);
    public List<BetterBeacons> getAll();
    public void save(BetterBeacons beacon);
    public void remove(BetterBeacons beacon);
    public void flush();
}
