_mine_block(inventory, b) -> (
    destroy(b, 'air');

    // Store in 'inventory' if not air
    bname = str('%s', b);
    if(bname != 'air' && bname != 'cave_air',
        if(get(inventory, bname) == null, put(inventory, bname, 0));

        put(inventory, bname, get(inventory, bname) + 1);
    );

    // Mine ores in neighbouring blocks
    for(neighbours(b), if(_is_ore(_), _mine_block(inventory, _)));
);

_is_ore(b) -> (
    return (first(l(
        'coal_ore',
        'iron_ore',
        'lapis_ore',
        'gold_ore',
        'redstone_ore',
        'emerald_ore',
        'diamond_ore'), b == _) != null);
);

_mine_strip(inventory, x, y, z, dirx, diry, dirz, len) -> (
    print('Mining strip @ ('+x+','+y+','+z+') with length '+len);
    cur_x = x;
    cur_y = y;
    cur_z = z;
    for(range(0, len),
        _mine_block(inventory, block(cur_x, cur_y + 0, cur_z));
        _mine_block(inventory, block(cur_x, cur_y + 1, cur_z));

        cur_x += dirx;
        cur_y += diry;
        cur_z += dirz;
    );
);

mine(ylevel, num_strips, strip_len, strip_gap) -> (
    inventory = m();

    for(range(0, num_strips),
        _mine_strip(inventory, 0, ylevel, _ * strip_gap, 1, 0, 0, strip_len);
    );

    print(sort(inventory));
);
