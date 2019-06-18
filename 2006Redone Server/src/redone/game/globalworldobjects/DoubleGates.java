package redone.game.globalworldobjects;

import redone.game.players.Client;
/**
 * Single Gates
 * @author Andrew (I'm A Boss on Rune-Server and Mr Extremez on Mopar & Runelocus)
 */

public class DoubleGates extends GateHandler {
	
	public static void useDoubleGate(Client player, int objectId) {
		switch (objectId) {
			case 7049:
				handleGate(player, 7049, 7050, 3077, 3259, 3077, 3260, 3077, 3258, 3078, 3258, 2);
			break;
			case 7050:
				handleGate(player, 7050, 7049, 3077, 3260, 3077, 3259, 3078, 3258, 3077, 3258, 2);
			break;
			case 1598:
				handleGate(player, 1598, 1599, 3319, 3467, 3318, 3467, 3320, 3467, 3320, 3468, 1);
			break;
			case 1599:
				handleGate(player, 1599, 1598, 3318, 3467, 3319, 3467, 3320, 3468, 3320, 3467, 1);
			break;
			case 3197:
				handleGate(player, 3197, 3198, 3311, 3234, 3310, 3234, 3312, 3234, 3312, 3235, 1);
			break;
			case 3198:
				handleGate(player, 3198, 3197, 3310, 3234, 3311, 3234, 3312, 3235, 3312, 3234, 1);
			break;
			case 1551:
			if (player.objectX > 3179 && player.objectX < 3182) {
				handleGate(player, 1551, 1553, 3181, 3288, 3181, 3287, 3181, 3289, 3180, 3289, 0);
			} else if (player.objectX > 3161 && player.objectX < 3164) {
				handleGate(player, 1551, 1553, 3163, 3289, 3163, 3288, 3163, 3290, 3162, 3290, 0);
			} else if (player.objectY > 3290 && player.objectY < 3294) {
				handleGate(player, 1551, 1553, 3145, 3292, 3145, 3293, 3145, 3291, 3146, 3291, 2);
			} else if (player.objectY > 3272 && player.objectY < 3275) {
				handleGate(player, 1551, 1553, 3106, 3273, 3105, 3273, 3107, 3273, 3107, 3274, 1);
			} else if (player.objectY > 3265 && player.objectY < 3268) {
				handleGate(player, 1551, 1553, 3252, 3266, 3251, 3266, 3253, 3266, 3253, 3267, 1);
			} else if (player.objectY > 3283 && player.objectY < 3286) {
				handleGate(player, 1551, 1553, 3237, 3285, 3238, 3285, 3236, 3285, 3236, 3284, 5);
			} else if (player.objectY > 3294 && player.objectY < 3297) {
				handleGate(player, 1551, 1553, 3237, 3296, 3238, 3296, 3236, 3296, 3236, 3295, 5);
			} else if (player.objectY > 3300 && player.objectY < 3303) {
				handleGate(player, 1551, 1553, 3240, 3301, 3239, 3301, 3241, 3301, 3241, 3302, 1);
			} else if (player.objectX > 3260 & player.objectX < 3263) {
				handleGate(player, 1551, 1553, 3261, 3322, 3261, 3323, 3261, 3321, 3262, 3321, 2);
			} else if (player.objectY > 3348 && player.objectY < 3351) {
				handleGate(player, 1551, 1553, 2676, 3350, 2677, 3350, 2675, 3350, 2675, 3349, 5);
			} else if (player.objectY > 3313 && player.objectY < 3318) {
				handleGate(player, 1551, 1553, 3174, 3314, 3174, 3315, 3175, 3316, 3174, 3316, 4);
			} else {
				handleGate(player, 1551, 1553, 3198, 3281, 3198, 3280, 3198, 3282, 3197, 3282, 0);
			}
			break;
			case 1553:
			if (player.objectX > 3179 && player.objectX < 3182) {
				handleGate(player, 1553, 1551, 3181, 3287, 3181, 3288, 3180, 3289, 3181, 3289, 0);
			} else if (player.objectX > 3161 && player.objectX < 3164) {
				handleGate(player, 1553, 1551, 3163, 3288, 3163, 3289, 3162, 3290, 3163, 3290, 0);
			} else if (player.objectY > 3290 && player.objectY < 3294) {
				handleGate(player, 1553, 1551, 3145, 3293, 3145, 3292, 3146, 3291, 3145, 3291, 2);
			} else if (player.objectY > 3272 && player.objectY < 3275) {
				handleGate(player, 1553, 1551, 3105, 3273, 3106, 3273, 3107, 3274, 3107, 3273, 1);
			} else if (player.objectY > 3265 && player.objectY < 3268) {
				handleGate(player, 1553, 1551, 3251, 3266, 3252, 3266, 3253, 3267, 3253, 3266, 1);
			} else if (player.objectY > 3283 && player.objectY < 3286) {
				handleGate(player, 1553, 1551, 3238, 3285, 3237, 3285, 3236, 3284, 3236, 3285, 5);
			} else if (player.objectY > 3294 && player.objectY < 3297) {
				handleGate(player, 1553, 1551, 3238, 3296, 3237, 3296, 3236, 3295, 3236, 3296, 5);
			} else if (player.objectY > 3300 && player.objectY < 3303) {
				handleGate(player, 1553, 1551, 3239, 3301, 3240, 3301, 3241, 3302, 3241, 3301, 1);
			} else if (player.objectX > 3260 & player.objectX < 3263) {
				handleGate(player, 1553, 1551, 3261, 3323, 3261, 3322, 3262, 3321, 3261, 3321, 2);
			} else if (player.objectY > 3348 && player.objectY < 3351) {
				handleGate(player, 1553, 1551, 2677, 3350, 2676, 3350, 2675, 3349, 2675, 3350, 5);
			} else if (player.objectY > 3313 && player.objectY < 3318) {
				handleGate(player, 1553, 1551, 3174, 3315, 3174, 3314, 3174, 3316, 3175, 3316, 4);
			} else {
				handleGate(player, 1553, 1551, 3198, 3280, 3198, 3281, 3197, 3282, 3198, 3282, 0);
			}
			break;
			case 12986:
			if (player.objectX > 3187 && player.objectX < 3190) {
				handleGate(player, 12986, 12987, 3188, 3280, 3188, 3281, 3188, 3279, 3189, 3279, 2);
			} else if (player.objectY > 3267 && player.objectY < 3270) {
				handleGate(player, 12986, 12987, 3185, 3268, 3184, 3268, 3186, 3268, 3186, 3269, 1);
			} else {
				handleGate(player, 12986, 12987, 3212, 3261, 3211, 3261, 3213, 3261, 3213, 3262, 1);	
			}
			break;
			case 12987:
			if (player.objectX > 3187 && player.objectX < 3190) {
				handleGate(player, 12987, 12986, 3188, 3281, 3188, 3280, 3189, 3279, 3188, 3279, 2);
			} else if (player.objectY > 3267 && player.objectY < 3270) {
				handleGate(player, 12987, 12986, 3184, 3268, 3185, 3268, 3186, 3269, 3186, 3268, 1);
			} else {
				handleGate(player, 12987, 12986, 3211, 3261, 3212, 3261, 3213, 3262, 3213, 3261, 1);
			}
			break;
			case 1596:
			if (player.objectX > 3310 && player.objectX < 3313 && player.absY != 3333) {
				handleSpecialGate(player, 1596, 1597, 3311, 3331, 3311, 3332, 3312, 3331, 3312, 3332, 3);
			} else if (player.objectY > 3318 && player.objectY < 3321 && player.absX != 2935 && player.absX != 2932) {
				handleSpecialGate(player, 1596, 1597, 2934, 3319, 2933, 3319, 2934, 3320, 2933, 3320, 0);
			} else if (player.objectX > 3130 && player.objectX < 3133) {
				handleSpecialGate(player, 1596, 1597, 3131, 9918, 3132, 9918, 3131, 9917, 3132, 9917, 6);
			} else if (player.objectY > 3181 && player.objectY < 3184) {
				handleSpecialGate(player, 1596, 1597, 2815, 3182, 2815, 3183, 2816, 3182, 2816, 3183, 3);
			} else if (player.objectY > 3449 && player.objectY < 3452) {
				handleSpecialGate(player, 1596, 1597, 2936, 3451, 2936, 3450, 2935, 3451, 2935, 3450, 7);
			}
			break;
			case 1597:
			if (player.objectX > 3310 && player.objectX < 3313 && player.absY != 3333) {
				handleSpecialGate(player, 1597, 1596, 3311, 3332, 3311, 3331, 3312, 3332, 3312, 3331, 1);
			} else if (player.objectY > 3318 && player.objectY < 3321 && player.absX != 2935 && player.absX != 2932) {
				handleSpecialGate(player, 1597, 1596, 2933, 3319, 2934, 3319, 2933, 3320, 2934, 3320, 2);
			} else if (player.objectX > 3130 && player.objectX < 3133) {
				handleSpecialGate(player, 1597, 1596, 3132, 9918, 3131, 9918, 3132, 9917, 3131, 9917, 4);
			} else if (player.objectY > 3181 && player.objectY < 3184) {
				handleSpecialGate(player, 1597, 1596, 2815, 3183, 2815, 3182, 2816, 3183, 2816, 3182, 1);
			} else if (player.objectY > 3449 && player.objectY < 3452) {
				handleSpecialGate(player, 1597, 1596, 2936, 3450, 2936, 3451, 2935, 3450, 2935, 3451, 5);
			}
			break;
			case 1557:
			if (player.objectY > 9830 && player.objectY < 9833 && player.absY != 9830) {
				handleSpecialGate(player, 1557, 1558, 2897, 9831, 2897, 9832, 2898, 9831, 2898, 9832, 3);
			} else if (player.objectY > 3866 && player.objectY < 3869) {
				handleSpecialGate(player, 1557, 1558, 3075, 3868, 3076, 3868, 3075, 3867, 3076, 3867, 4);
			/*Heroes Guild*/
			} else if (player.objectX > 2908 && player.objectX < 2911) {
				//newx, newy, newx2, newy2, oldx, oldy, oldx2, oldy2
				handleSpecialGate(player, 1557, 1558, 2910, 9909, 2909, 9909, 2910, 9910, 2909, 9910, 0);
			} else if (player.objectY > 9908 && player.objectY < 9911) {
				handleSpecialGate(player, 1557, 1558, 3104, 9910, 3104, 9909, 3103, 9910, 3103, 9909, 7);
			} else if (player.objectX > 3110 && player.objectX < 3113 && player.absY != 3513 && player.absY != 3516) {
				handleSpecialGate(player, 1557, 1558, 3111, 3514, 3111, 3515, 3112, 3514, 3112, 3515, 3);
			} else if (player.objectX > 3104 && player.objectX < 3107 && player.absX != 3104 && player.absX != 3107) {
				handleSpecialGate(player, 1557, 1558, 3105, 9945, 3106, 9945, 3105, 9944, 3106, 9944, 6);
			} else if (player.objectX > 3144 && player.objectX < 3147) {
				handleSpecialGate(player, 1558, 1557, 3146, 9871, 3146, 9870, 3145, 9871, 3145, 9870, 1);
			}
			break;
			case 1558:
			if (player.objectY > 9830 && player.objectY < 9833 && player.absY != 9830) {
				handleSpecialGate(player, 1558, 1557, 2897, 9832, 2897, 9831, 2898, 9832, 2898, 9831, 1);
			} else if (player.objectY > 3866 && player.objectY < 3869) {
				handleSpecialGate(player, 1558, 1557, 3076, 3868, 3075, 3868, 3076, 3867, 3075, 3867, 4);
			/* Heroes Guild*/
			} else if (player.objectX > 2908 && player.objectX < 2911) {
				//newx, newy, newx2, newy2, oldx, oldy, oldx2, oldy2
				handleSpecialGate(player, 1558, 1557, 2909, 9909, 2910, 9909, 2909, 9910, 2910, 9910, 2);
			} else if (player.objectY > 9908 && player.objectY < 9911) {
				handleSpecialGate(player, 1558, 1557, 3104, 9909, 3104, 9910, 3103, 9909, 3103, 9910, 5);
			} else if (player.objectX > 3110 && player.objectX < 3113 && player.absY != 3513 && player.absY != 3516) {
				handleSpecialGate(player, 1558, 1557, 3111, 3515, 3111, 3514, 3112, 3515, 3112, 3514, 3);
			} else if (player.objectX > 3104 && player.objectX < 3107 && player.absX != 3104 && player.absX != 3107) {
				handleSpecialGate(player, 1558, 1557, 3106, 9945, 3105, 9945, 3106, 9944, 3105, 9944, 4);
			} else if (player.objectX > 3144 && player.objectX < 3147) {
				handleSpecialGate(player, 1558, 1557, 3146, 9870, 3146, 9871, 3145, 9870, 3145, 9871, 5);
			}
			break;
			case 3506:
			if (player.absX != 3445 && player.absX != 3442) {
				openSpecialWalkGate(player, 3506, 3507, 3444, 3457, 3443, 3457, 3444, 3458, 3443, 3458, 0, player.absY == 3457 ? 1 : -1, 2, 0, 3);
			}
			break;
			case 3507:
			if (player.absX != 3445 && player.absX != 3442) {
				openSpecialWalkGate(player, 3507, 3506, 3443, 3457, 3444, 3457, 3443, 3458, 3444, 3458, 0, player.absY == 3457 ? 1 : -1, 0, 2, 3);
			}
			break;
		}
	}

}
