export default interface UserMemoryOfChaos {
  [schedule_id: number]: {
    begin_time: {
      year: number;
      month: number;
      day: number;
      hour: number;
      minute: number;
    };
    end_time: {
      year: number;
      month: number;
      day: number;
      hour: number;
      minute: number;
    };
    star_num: number;
    battle_num: number;
    max_floor_id: number;
    max_floor: number;
    all_floor_detail: {
      floor_id: number;
      round_num: number;
      star_num: number;
      layer_1: {
        challenge_time: {
          year: number;
          month: number;
          day: number;
          hour: number;
          minute: number;
        };
        characters: {
          id: number;
          level: number;
          rank: number;
        }[];
      };
      layer_2: {
        challenge_time: {
          year: number;
          month: number;
          day: number;
          hour: number;
          minute: number;
        };
        characters: {
          id: number;
          level: number;
          rank: number;
        }[];
      };
    }[];
  };
}
