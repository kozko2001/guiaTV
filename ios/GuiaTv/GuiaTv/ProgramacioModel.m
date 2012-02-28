//
//  ProgramacioModel.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 25/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ProgramacioModel.h"

@implementation ProgramacioModel

- (ProgramacioModel*) initFromProgramas: (NSArray*) programas
{
    self = [self init];
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] initWithCapacity: 10];
    
    for( ProgramaModel *programa in programas)
    {
        NSNumber *key = [NSNumber numberWithInt: programa.start / 10000];
        NSLog(@"%@", key);
        
        if( [dict objectForKey: key] == nil)
            [dict setObject: [[NSMutableArray alloc] initWithCapacity:10] forKey: key];
        
        NSMutableArray *programasInSection = (NSMutableArray* ) [dict objectForKey: key];
        
        [programasInSection addObject: programa];
    }
    NSComparator dateComparer = ^(NSNumber* obj1, NSNumber* obj2) {
        return [obj1 compare: obj2] ;
    };

    
    keys =     [[dict allKeys] sortedArrayUsingComparator:dateComparer];
            NSLog(@"------");
    for(NSNumber* k in keys)
    {
        NSLog(@"%d" , [k intValue]);
    }
    NSMutableArray *_sections = [[NSMutableArray alloc] initWithCapacity: [keys count]];
    

    for (NSNumber* key in keys) {
        [_sections addObject: [(NSMutableArray*)[dict objectForKey: key] copy]];
    }

    sections = [_sections copy];
    
    return self;
}
- (int) numDias
{
    return [sections count];
}
- (int) numProgramasByDia: (int) dia
{
    NSArray *programas = (NSArray*) [sections objectAtIndex: dia];
    return [programas count];
}

- (ProgramaModel*) getProgramaByDia: (int) dia AndRow:(int) row
{
    NSArray *programas = (NSArray*) [sections objectAtIndex: dia];
    return (ProgramaModel*) [programas objectAtIndex: row];
}

- (int) getSectionDate: (int) section
{
    return [(NSNumber*)[keys objectAtIndex: section] intValue];
}
@end
