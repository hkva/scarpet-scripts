_calculate_open_water(pos) -> (
    water_type = 'INVALID';

    draw_shape(
        'box',
        20 * 10,
        'from',
        l(pos:0 - 2, pos:1 - 1, pos:2 - 2),
        'to',
        l(pos:0 + 2, pos:1 + 2, pos:2 + 2),
    );

    c_for(y = -1, y <= 2, y += 1,
        p1 = l(pos:0 - 2, pos:1 + y, pos:2 - 2);
        p2 = l(pos:0 + 2, pos:1 + y, pos:2 + 2);

        layer_type = _get_open_water_type_for_area(p1, p2);

        if (layer_type == 'INVALID', return(false));
        if (layer_type == 'ABOVE_WATER' && water_type == 'INVALID', return(false));
        if (layer_type == 'INSIDE_WATER' && water_type == 'ABOVE_WATER', return(false));

        water_type = layer_type;
    );

    return(true);
);

_get_open_water_type_for_area(p1, p2) -> (
    blocks = l();
    c_for(x = p1:0, x <= p2:0, x += 1,
        c_for(y = p1:1, y <= p2:1, y += 1,
            c_for(z = p1:2, z <= p2:2, z += 1,
                blocks += l(x, y, z);
            );
        );
    );

    types = map(blocks, _get_open_water_type_for_block(_));
    result = reduce(types, if (_a == _, _, 'INVALID'), types:0);

    return(result);
);

_get_open_water_type_for_block(pos) -> (
    b = block(pos);
    
    if (air(b) || b == block('lily_pad'), return('ABOVE_WATER'));

    // Bug: This doesn't work correctly for flowing water
    if (b == block('water'), return('INSIDE_WATER'));

    return('INVALID');
);

_annotate_open_water(e) -> (
    is_open_water = _calculate_open_water(e ~ 'pos');
    modify(e, 'custom_name', 'In open water: ' + if (is_open_water, 'True', 'False'));
);

debug_open_water() -> (
    for(
        entity_selector('@e[type=minecraft:fishing_bobber]'),
        _annotate_open_water(_)
    );
);
