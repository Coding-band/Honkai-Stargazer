import officalRelicId from "../../../map/relic_offical_id_map";

export default function getSetIdAndCountFromRelicData(relicData: any) {
  const relicSetId = Number(relicData.set_id);
  const relicCount =
    Number(
      relicData.icon.split(".")[0][relicData.icon.split(".")[0].length - 1]
    ) + 1;

  return [relicSetId, relicCount];
}
